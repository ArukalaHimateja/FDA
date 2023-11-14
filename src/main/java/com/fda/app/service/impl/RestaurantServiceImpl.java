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
import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.RestaurantFilterWithPaginationDto;
import com.fda.app.dto.RestaurantRequestDto;
import com.fda.app.dto.RestaurantRequestFilterWithPaginationDto;
import com.fda.app.dto.RestaurantUpdateRequestDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.GLocation;
import com.fda.app.model.Restaurant;
import com.fda.app.model.RestaurantDocument;
import com.fda.app.model.RestaurantRequest;
import com.fda.app.model.User;
import com.fda.app.repository.LocationRepository;
import com.fda.app.repository.RestaurantDocumentRepository;
import com.fda.app.repository.RestaurantRepository;
import com.fda.app.repository.RestaurantRequestRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.repository.custom.RestaurantRepositoryCustom;
import com.fda.app.repository.custom.RestaurantRequestRepositoryCustom;
import com.fda.app.service.IRestaurantService;
import com.fda.app.service.IVerificationTokenService;
import com.fda.app.utility.Utility;
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
	@Autowired
	private RestaurantDocumentRepository restaurantDocumentRepository;
	@Autowired
	private RestaurantRequestRepository restaurantRequestRepository;
	@Autowired
	private RestaurantRequestRepositoryCustom restaurantRequestRepositoryCustom;
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
		if (restaurantRepository.existsByRestaurantEmail(restaurantRequestDto.getEmail())) {
			apiResponseDtoBuilder.withMessage(Constants.RESTAURANT_EMAIL_ALREADY_EXISTS)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;

		}
		if (userRepository.existsByMobileNumber(restaurantRequestDto.getMobileNumber())) {
			apiResponseDtoBuilder.withMessage(Constants.MOBILE_NUMBER_ALREADY_EXISTS)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if (userRepository.existsByEmail(restaurantRequestDto.getEmail())) {
			apiResponseDtoBuilder.withMessage(Constants.EMAIL_ALREADY_EXISTS).withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if (restaurantRequestRepository.existsByEmail(restaurantRequestDto.getEmail())) {
			RestaurantRequest restaurantRequest = restaurantRequestRepository
					.findByEmail(restaurantRequestDto.getEmail());
			if (restaurantRequest.getStatus() == 0 || restaurantRequest.getStatus() == 1) {
				apiResponseDtoBuilder.withMessage(Constants.RESTAURANT_REQUEST_ALREADY_EXISTS)
						.withStatus(HttpStatus.ALREADY_REPORTED);
				return;
			}

		}

		RestaurantRequest restaurantRequest = customMapper
				.restaurantRequestDtoToRestaurantRequest(restaurantRequestDto);
		restaurantRequest.setCreatedAt(new Date());
		restaurantRequestRepository.save(restaurantRequest);

		List<String> listOfDocuments = restaurantRequestDto.getDocuments();
		if (listOfDocuments != null) {
			for (int i = 0; i < listOfDocuments.size(); i++) {
				RestaurantDocument restaurantDocument = new RestaurantDocument();
				restaurantDocument.setRestaurantRequestId(restaurantRequest.getId());
				restaurantDocument.setDocument(listOfDocuments.get(i));
				restaurantDocumentRepository.save(restaurantDocument);
			}
		}
		apiResponseDtoBuilder.withMessage(Constants.RESTAURANT_ADD_REQUEST_SEND).withStatus(HttpStatus.OK)
				.withData(restaurantRequest);

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
	public void updateRestaurant(@Valid RestaurantUpdateRequestDto restaurantDto, long id,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Restaurant> restaurant = restaurantRepository.findById(id);
		if (restaurant.isPresent()) {
			Optional<User> userDb = userRepository.findById(restaurant.get().getUserId());
			if (userDb.isPresent()) {
				userDb.get().setFullName(restaurantDto.getOwnerName());
				userDb.get().setEmail(restaurantDto.getEmail());
				userDb.get().setMobileNumber(restaurantDto.getMobileNumber());
				userDb.get().setAddress(restaurantDto.getRestaurantAddress());
				userDb.get().setUpdatedAt(new Date());
				userRepository.save(userDb.get());
				apiResponseDtoBuilder.withMessage(Constants.USER_UPDATED_SUCCESSFULLY).withStatus(HttpStatus.OK)
						.withData(userDb);
			} else {
				apiResponseDtoBuilder.withMessage(Constants.USER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
			}
			restaurant.get().setUpdatedAt(new Date());
			restaurant.get().setRestaurantAddress(restaurantDto.getRestaurantAddress());
			restaurant.get().setRestaurantLicenseNumber(restaurantDto.getRestaurantLicenseNumber());
			restaurant.get().setRestaurantEmail(restaurantDto.getEmail());
			restaurant.get().setRestaurantName(restaurantDto.getRestaurantName());
			restaurantRepository.save(restaurant.get());
			apiResponseDtoBuilder.withMessage(Constants.RESTAURANT_UPDATED).withStatus(HttpStatus.OK)
					.withData(restaurant);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void searchRestaurant(String keyword, ApiResponseDtoBuilder apiResponseDtoBuilder) {

		List<Restaurant> customerList = restaurantRepositoryCustom.getRestaurantListByRestaurantName(keyword);
		apiResponseDtoBuilder.withStatus(HttpStatus.OK).withMessage(Constants.SUCCESS).withData(customerList);

	}

	@Override
	public void adminApprovedRestaurantById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User currentUser = Utility.getSessionUser(userRepository);
		if (currentUser.getRole() != 0) {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
		Optional<RestaurantRequest> restaurantRequest = restaurantRequestRepository.findById(id);
		if (restaurantRequest.isPresent()) {
			User user = new User();
			user.setAddress(restaurantRequest.get().getRestaurantAddress());
			user.setEmail(restaurantRequest.get().getEmail());
			user.setFullName(restaurantRequest.get().getOwnerName());
			user.setMobileNumber(restaurantRequest.get().getMobileNumber());
			String randomPassword = Utility.generateRandomPassword(8);
			System.out.println(randomPassword);
			String newPasswordEncodedString = bCryptPasswordEncoder.encode(randomPassword);
			user.setPassword(newPasswordEncodedString);
			user.setRole(2);
			user.setCreatedAt(new Date());
			userRepository.save(user);

			Restaurant restaurant = new Restaurant();
			restaurant.setRestaurantAddress(restaurantRequest.get().getRestaurantAddress());
			restaurant.setRestaurantEmail(restaurantRequest.get().getEmail());
			restaurant.setRestaurantLicenseNumber(restaurantRequest.get().getRestaurantLicenseNumber());
			restaurant.setRestaurantName(restaurantRequest.get().getRestaurantName());
			restaurant.setRestaurantRequestId(restaurantRequest.get().getId());
			restaurant.setRestaurantMobileNumber(restaurantRequest.get().getMobileNumber());
			restaurant.setCreatedAt(new Date());
			restaurant.setUserId(user.getId());
			restaurant.setStatus(1);
			restaurantRepository.save(restaurant);

			restaurantRequest.get().setStatus(1);
			restaurantRequest.get().setUpdatedAt(new Date());
			restaurantRequestRepository.save(restaurantRequest.get());

			try {
				GeocodingResult[] results;
				results = GeocodingApi.geocode(context, restaurant.getRestaurantAddress()).await();
				GLocation location = parseLocationJson(results);
				location.setRestaurantId(restaurant.getId());
				location.setCreatedAt(new Date());
				locationRepository.save(location);
			} catch (ApiException e) {

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			List<RestaurantDocument> listOfRestaurantDocument = restaurantDocumentRepository
					.findByRestaurantRequestId(restaurantRequest.get().getId());
			for (RestaurantDocument restaurantDocument : listOfRestaurantDocument) {
				restaurantDocument.setRestaurantId(restaurant.getId());
				restaurantDocumentRepository.save(restaurantDocument);
			}

			apiResponseDtoBuilder.withMessage(Constants.RESTAURANT_ACCEPTED).withStatus(HttpStatus.OK)
					.withData(restaurant);
			new Thread(() -> {
				verificationTokenService.sendRestaurantRandomPasswordAndVerificationToken(restaurant, randomPassword);
			}).start();
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void adminRejectRestaurantById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {

		User currentUser = Utility.getSessionUser(userRepository);
		if (currentUser.getRole() != 0) {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
		Optional<RestaurantRequest> restaurantRequest = restaurantRequestRepository.findById(id);
		if (restaurantRequest.isPresent()) {
			restaurantRequest.get().setStatus(2);
			restaurantRequestRepository.save(restaurantRequest.get());
			apiResponseDtoBuilder.withMessage(Constants.RESTAURANT_REQUEST_REJECT).withStatus(HttpStatus.OK)
					.withData(restaurantRequest.get());
			new Thread(() -> {
				verificationTokenService.sendRejectRestaurantRequestEmail(restaurantRequest.get());
			}).start();
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void getRestaurantDocumentByRestaurantRequestId(long restaurantRequestId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<RestaurantDocument> restaurantDocument = restaurantDocumentRepository
				.findByRestaurantRequestId(restaurantRequestId);
		if (restaurantDocument != null) {
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(restaurantDocument);
		} else {

			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void getAllListOfRestaurantRequest(
			RestaurantRequestFilterWithPaginationDto restaurantRequestFilterWithPaginationDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		PaginationDto pagination = restaurantRequestRepositoryCustom
				.getRestaurantRequestListByFilterWithPagination(restaurantRequestFilterWithPaginationDto);
		apiResponseDtoBuilder.withMessage(Constants.DATA_LIST).withStatus(HttpStatus.OK).withData(pagination);

	}

	@Override
	public void searchRestaurantFilterWithPagination(RestaurantFilterWithPaginationDto filterWithPagination,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		PaginationDto pagination = restaurantRepositoryCustom
				.getRestaurantListByFilterWithPagination(filterWithPagination);
		apiResponseDtoBuilder.withStatus(HttpStatus.OK).withMessage(Constants.SUCCESS).withData(pagination);

	}

	@Override
	public void isActiveRestaurant(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Restaurant> restaurant = restaurantRepository.findById(id);
		if (restaurant.isPresent()) {
			restaurant.get().setStatus(1);
			save(restaurant.get());
			apiResponseDtoBuilder.withMessage(Constants.RESTAURANT_ACTIVE_SUCCESS).withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.RESTAURANT_ID_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void isInactiveRestaurant(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Restaurant> restaurant = restaurantRepository.findById(id);
		if (restaurant.isPresent()) {
			restaurant.get().setStatus(0);
			save(restaurant.get());
			apiResponseDtoBuilder.withMessage(Constants.RESTAURANT_DEACTIVE_SUCCESS).withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.RESTAURANT_ID_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void getAllListOfRestaurant(RestaurantRequestFilterWithPaginationDto restaurantFilterWithPaginationDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		PaginationDto pagination = restaurantRepositoryCustom
				.getRestaurantListByFilterWithPagination(restaurantFilterWithPaginationDto);
		apiResponseDtoBuilder.withMessage(Constants.DATA_LIST).withStatus(HttpStatus.OK).withData(pagination);

	}

	@Override
	public void getRestaurantRequestById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<RestaurantRequest> restaurant = restaurantRequestRepository.findById(id);
		if (restaurant.isPresent()) {
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(restaurant);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void getRestaurantRequestDocumentById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<RestaurantDocument> restaurantDocument = restaurantDocumentRepository.findById(id);
		if (restaurantDocument != null) {
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(restaurantDocument);
		} else {

			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}


}
