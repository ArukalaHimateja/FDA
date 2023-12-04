package com.fda.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.User;
import com.fda.app.repository.OrderRepository;
import com.fda.app.repository.RestaurantRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.service.impl.DashboardServiceImpl;
import com.fda.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class DashboardServiceImplTest {
	@InjectMocks
	DashboardServiceImpl dashboardServiceImpl;

	@Mock
	UserRepository userRepository;
	@Mock
	CustomMapper customMapper;
	@Mock
	OrderRepository orderRepository;
	@Mock
	RestaurantRepository restaurantRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void getAllDetails() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User user = new User();
		String email = "test123@gmail.com";
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		user.setRole(0);
		when(Utility.getSessionUser(userRepository)).thenReturn(user);
		long totalEmployee = 10;
		when(userRepository.countByRole(3)).thenReturn(totalEmployee);
		when(userRepository.countByRole(1)).thenReturn(totalEmployee);
		when(orderRepository.count()).thenReturn(totalEmployee);
		when(orderRepository.countByStatus(0)).thenReturn(totalEmployee);
		when(orderRepository.countByStatus(3)).thenReturn(totalEmployee);
		when(restaurantRepository.count()).thenReturn(totalEmployee);
		dashboardServiceImpl.getAllDetails(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}
}
