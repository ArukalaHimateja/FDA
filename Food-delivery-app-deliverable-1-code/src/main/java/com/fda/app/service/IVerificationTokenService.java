package com.fda.app.service;

import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.model.User;

@Service
public interface IVerificationTokenService {

	String validateToken(String token);

	void resendRegistrationToken(Long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void sendVerificationToken(User user);

<<<<<<< HEAD
=======
	void sendRejectRestaurantRequestEmail(RestaurantRequest restaurant);

	void sendRestaurantRandomPasswordAndVerificationToken(Restaurant restaurant, String randomPassword);

	void sendContectEmail(@Valid ContactDto contactDto);

>>>>>>> d3b79d566c932f262577726729be4822ab33c101
}
