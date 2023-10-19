package com.fda.app.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.RestaurantDto;
import com.fda.app.dto.RestaurantRequestDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.GLocation;
import com.fda.app.model.Restaurant;
import com.fda.app.model.User;
import com.fda.app.repository.LocationRepository;
import com.fda.app.repository.RestaurantRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.repository.custom.RestaurantRepositoryCustom;
import com.fda.app.service.IRestaurantService;
import com.fda.app.service.IVerificationTokenService;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

@Service
public class RestaurantServiceImpl implements IRestaurantService {
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private CustomMapper customMapper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IVerificationTokenService verificationTokenService;
	@Autowired
	private RestaurantRepositoryCustom restaurantRepositoryCustom;
	@Autowired
	private LocationRepository locationRepository;

	@Value("AIzaSyA1ii_WyZ-ecQUcodDZuBUom2IpdqeiswU")
	private String googleApiKey;

	private GeoApiContext context;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostConstruct
	private void createGeoApiContext() {
		context = new GeoApiContext.Builder().apiKey(googleApiKey).build();
	}

	@Override
	public void addRestaurant(@Valid RestaurantRequestDto restaurantRequestDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {

		if (userRepository.existsByMobileNumber(restaurantRequestDto.getUserRequestDto().getMobileNumber())) {
			apiResponseDtoBuilder.withMessage(Constants.MOBILE_NUMBER_ALREADY_EXISTS)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if (userRepository.existsByEmail(restaurantRequestDto.getUserRequestDto().getEmail())) {
			apiResponseDtoBuilder.withMessage(Constants.EMAIL_ALREADY_EXISTS).withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if (restaurantRepository
				.existsByRestaurantGstNo(restaurantRequestDto.getRestaurantDto().getRestaurantGstNo())) {
			apiResponseDtoBuilder.withMessage(Constants.GST_ALREADY_EXISTS).withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if (restaurantRepository
				.existsByOwnerAadharNumber(restaurantRequestDto.getRestaurantDto().getOwnerAadharNumber())) {
			apiResponseDtoBuilder.withMessage(Constants.AADHER_ALREADY_EXISTS).withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		User user = customMapper.userRequestDtoToUser(restaurantRequestDto.getUserRequestDto());
		String newPasswordEncodedString = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(newPasswordEncodedString);
		user.setCreatedAt(new Date());
		userRepository.save(user);

		Restaurant restaurant = customMapper.restaurantDtoToRestaurant(restaurantRequestDto.getRestaurantDto());
		restaurant.setCreatedAt(new Date());
		restaurant.setUserId(user.getId());
		save(restaurant);
		GeocodingResult[] results;

		try {
			results = GeocodingApi.geocode(context, restaurant.getRestaurantAddress()).await();
			GLocation location = parseLocationJson(results);
			location.setRestaurantId(restaurant.getId());
			location.setCreatedAt(new Date());
			locationRepository.save(location);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		apiResponseDtoBuilder.withMessage(Constants.RESTAURANT_ADD_SUCCESSFULLY).withStatus(HttpStatus.OK)
				.withData(restaurant);
		new Thread(() -> {
			verificationTokenService.sendRestaurantVerificationToken(restaurant);
		}).start();

	}

	private GLocation parseLocationJson(GeocodingResult[] results) {
		GLocation gLocation = new GLocation();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(results);
			JsonNode jsonNode = objectMapper.readTree(json);
			gLocation.setAddress(jsonNode.get(0).path("formattedAddress").asText());
			gLocation.setLocationLat(
					Double.parseDouble(jsonNode.get(0).path("geometry").path("location").path("lat").asText()));
			gLocation.setLocationLng(
					Double.parseDouble(jsonNode.get(0).path("geometry").path("location").path("lng").asText()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return gLocation;
	}

	@Override
	public void save(Restaurant restaurant) {
		restaurantRepository.save(restaurant);

	}

	@Override
	public void getRestaurantById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Restaurant> restaurant = restaurantRepository.findById(id);
		if (restaurant.isPresent()) {
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(restaurant);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void getAllRestaurant(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Restaurant> restaurantList = restaurantRepository.findAll();
		apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(restaurantList);

	}

	@Override
	public void getRestaurantByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Restaurant> restaurant = restaurantRepository.findByUserId(userId);
		if (restaurant.isPresent()) {
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(restaurant);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void updateRestaurant(@Valid RestaurantDto restaurantDto, long id,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Restaurant> restaurant = restaurantRepository.findById(id);
		if (restaurant.isPresent()) {
			restaurant.get().setUpdatedAt(new Date());
			restaurant.get().setRestaurantGstNo(restaurantDto.getRestaurantGstNo());
			restaurant.get().setRestaurantName(restaurantDto.getRestaurantName());
			restaurant.get().setOwnerAadharNumber(restaurantDto.getOwnerAadharNumber());
			restaurant.get().setRestaurantGstNo(restaurantDto.getRestaurantGstNo());
			restaurantRepository.save(restaurant.get());
			apiResponseDtoBuilder.withMessage(Constants.RESTAURANT_UPDATED).withStatus(HttpStatus.OK)
					.withData(restaurant);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void searchRestaurant(String restaurantName, ApiResponseDtoBuilder apiResponseDtoBuilder) {

		List<Restaurant> customerList = restaurantRepositoryCustom
				.getRestaurantListByFilterWithPagination(restaurantName);
		apiResponseDtoBuilder.withStatus(HttpStatus.OK).withMessage(Constants.SUCCESS).withData(customerList);

	}

}
