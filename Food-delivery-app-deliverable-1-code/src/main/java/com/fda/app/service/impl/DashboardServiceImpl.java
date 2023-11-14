package com.fda.app.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.DashboardResponseDto;
import com.fda.app.model.Restaurant;
import com.fda.app.model.User;
import com.fda.app.repository.RestaurantRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.service.IDashboardService;
import com.fda.app.utility.Utility;

@Service
public class DashboardServiceImpl implements IDashboardService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;

	@Override
	public void getAllDetails(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User currentUser = Utility.getSessionUser(userRepository);
		// currentUser.setRole(0);
		// currentUser.setId(17l);
		long totalEmployee = userRepository.countByRole(3);
		long totalCustomer = 0;
		long totalOrder = 0;
		long totalOrderDelivered = 0;
		long totalPendingOrder = 0l;
		if (currentUser.getRole() == 0) {
			DashboardResponseDto dashboardResponseDto = new DashboardResponseDto();
			totalCustomer = userRepository.countByRole(1);
			long totalRestaurant = restaurantRepository.count();
			dashboardResponseDto.setTotalOrder(totalOrder);
			dashboardResponseDto.setTotalRestaurant(totalRestaurant);
			dashboardResponseDto.setTotalUser(totalCustomer);
			dashboardResponseDto.setTotalEmployee(totalEmployee);
			dashboardResponseDto.setTotalOrderDelivered(totalOrderDelivered);
			dashboardResponseDto.setTotalPendingOrder(totalPendingOrder);
			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(dashboardResponseDto);
		} else if (currentUser.getRole() == 2) {
			DashboardResponseDto dashboardResponseDto = new DashboardResponseDto();
			Optional<Restaurant> restaurant = restaurantRepository.findByUserId(currentUser.getId());
			dashboardResponseDto.setTotalEmployee(totalEmployee);
			dashboardResponseDto.setTotalOrder(totalOrder);
			dashboardResponseDto.setTotalPendingOrder(totalPendingOrder);
			dashboardResponseDto.setTotalOrderDelivered(totalOrderDelivered);
			dashboardResponseDto.setTotalUser(totalCustomer);
			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(dashboardResponseDto);
		}

	}
}
package com.fda.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.DashboardResponseDto;
import com.fda.app.model.User;
import com.fda.app.repository.UserRepository;
import com.fda.app.service.IDashboardService;
import com.fda.app.utility.Utility;

@Service
public class DashboardServiceImpl implements IDashboardService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public void getAllDetails(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User currentUser = Utility.getSessionUser(userRepository);
		// currentUser.setRole(0);
		// currentUser.setId(17l);
		long totalEmployee = userRepository.countByRole(3);
		long totalCustomer = 0;
		long totalOrder = 0;
		long totalOrderDelivered = 0;
		long totalPendingOrder = 0l;
		if (currentUser.getRole() == 0) {
			DashboardResponseDto dashboardResponseDto = new DashboardResponseDto();
			totalCustomer = userRepository.countByRole(1);
			dashboardResponseDto.setTotalOrder(totalOrder);
			dashboardResponseDto.setTotalUser(totalCustomer);
			dashboardResponseDto.setTotalEmployee(totalEmployee);
			dashboardResponseDto.setTotalOrderDelivered(totalOrderDelivered);
			dashboardResponseDto.setTotalPendingOrder(totalPendingOrder);
			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(dashboardResponseDto);
		} else if (currentUser.getRole() == 2) {
			DashboardResponseDto dashboardResponseDto = new DashboardResponseDto();
			dashboardResponseDto.setTotalEmployee(totalEmployee);
			dashboardResponseDto.setTotalOrder(totalOrder);
			dashboardResponseDto.setTotalPendingOrder(totalPendingOrder);
			dashboardResponseDto.setTotalOrderDelivered(totalOrderDelivered);
			dashboardResponseDto.setTotalUser(totalCustomer);
			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(dashboardResponseDto);
		}

	}
}
