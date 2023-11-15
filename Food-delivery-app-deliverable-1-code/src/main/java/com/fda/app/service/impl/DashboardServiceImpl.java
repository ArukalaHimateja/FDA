package com.fda.app.service.impl;

<<<<<<< HEAD
=======
import java.util.Optional;

>>>>>>> d6e769532eb2fc0720d380657f9b84dfb7219f89
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.DashboardResponseDto;
<<<<<<< HEAD
import com.fda.app.model.User;
=======
import com.fda.app.model.Restaurant;
import com.fda.app.model.User;
import com.fda.app.repository.RestaurantRepository;
>>>>>>> d6e769532eb2fc0720d380657f9b84dfb7219f89
import com.fda.app.repository.UserRepository;
import com.fda.app.service.IDashboardService;
import com.fda.app.utility.Utility;

@Service
public class DashboardServiceImpl implements IDashboardService {
	@Autowired
	private UserRepository userRepository;
<<<<<<< HEAD
=======
	@Autowired
	private RestaurantRepository restaurantRepository;
>>>>>>> d6e769532eb2fc0720d380657f9b84dfb7219f89

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
<<<<<<< HEAD
			dashboardResponseDto.setTotalOrder(totalOrder);
=======
			long totalRestaurant = restaurantRepository.count();
			dashboardResponseDto.setTotalOrder(totalOrder);
			dashboardResponseDto.setTotalRestaurant(totalRestaurant);
>>>>>>> d6e769532eb2fc0720d380657f9b84dfb7219f89
			dashboardResponseDto.setTotalUser(totalCustomer);
			dashboardResponseDto.setTotalEmployee(totalEmployee);
			dashboardResponseDto.setTotalOrderDelivered(totalOrderDelivered);
			dashboardResponseDto.setTotalPendingOrder(totalPendingOrder);
			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(dashboardResponseDto);
		} else if (currentUser.getRole() == 2) {
			DashboardResponseDto dashboardResponseDto = new DashboardResponseDto();
<<<<<<< HEAD
=======
			Optional<Restaurant> restaurant = restaurantRepository.findByUserId(currentUser.getId());
>>>>>>> d6e769532eb2fc0720d380657f9b84dfb7219f89
			dashboardResponseDto.setTotalEmployee(totalEmployee);
			dashboardResponseDto.setTotalOrder(totalOrder);
			dashboardResponseDto.setTotalPendingOrder(totalPendingOrder);
			dashboardResponseDto.setTotalOrderDelivered(totalOrderDelivered);
			dashboardResponseDto.setTotalUser(totalCustomer);
			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(dashboardResponseDto);
		}

	}
}
