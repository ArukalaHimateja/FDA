package com.fda.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.RestaurantRequestDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.Restaurant;
import com.fda.app.model.User;
import com.fda.app.repository.RestaurantRepository;
import com.fda.app.repository.custom.UserRepositoryCustom;
import com.fda.app.service.IEmailService;
import com.fda.app.service.IVerificationTokenService;
import com.fda.app.service.impl.RestaurantServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceImplTest {
	@Mock
	CustomMapper customMapper;

	@InjectMocks
	private RestaurantServiceImpl restaurantServiceImpl;

	@Mock
	private RestaurantRepository restaurantRepository;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	private IEmailService emailService;

	@Mock
	private IVerificationTokenService verificationTokenService;
	@Mock
	private UserRepositoryCustom userRepositoryCustom;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addRestaurant() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();

		RestaurantRequestDto userDto = new RestaurantRequestDto();
		userDto.setEmail("testadmin@gmail.com");
		userDto.setMobileNumber("11111111");

		when(restaurantRepository.existsByRestaurantEmail(userDto.getEmail())).thenReturn(true);
		restaurantServiceImpl.addRestaurant(userDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.RESTAURANT_EMAIL_ALREADY_EXISTS));

	}

	@Test
	public void getRestaurantById() {
		long id = 3L;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Restaurant seller = new Restaurant();
		seller.setId(id);
		Optional<Restaurant> optional = Optional.of(seller);
		when(restaurantRepository.findById(id)).thenReturn(optional);
		assertTrue(optional.isPresent());
		restaurantServiceImpl.getRestaurantById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESS));
	}

	@Test
	public void getAllRestaurant() {
		long id = 3L;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		List<Restaurant> restaurantList = new ArrayList<>();
		Restaurant Restaurant = new Restaurant();
		Restaurant.setId(1l);
		when(restaurantRepository.findAll()).thenReturn(restaurantList);
		restaurantServiceImpl.getAllRestaurant(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESS));
	}

	@Test
	public void isActiveRestaurant() {
		long id = 0;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1l);
		Optional<Restaurant> optional = Optional.of(restaurant);
		when(restaurantRepository.findById(id)).thenReturn(optional);
		restaurantServiceImpl.isActiveRestaurant(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.RESTAURANT_ACTIVE_SUCCESS));
	}

	@Test
	public void isInactiveRestaurant() {
		long id = 0;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1l);
		Optional<Restaurant> optional = Optional.of(restaurant);
		when(restaurantRepository.findById(id)).thenReturn(optional);
		restaurantServiceImpl.isInactiveRestaurant(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.RESTAURANT_DEACTIVE_SUCCESS));
	}
}