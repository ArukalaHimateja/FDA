package com.fda.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.ContactDto;
import com.fda.app.model.Restaurant;
import com.fda.app.model.RestaurantRequest;
import com.fda.app.model.User;

@Service
public interface IVerificationTokenService {

	String validateToken(String token);

	void resendRegistrationToken(Long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void sendVerificationToken(User user);

	void sendRejectRestaurantRequestEmail(RestaurantRequest restaurant);

	void sendRestaurantRandomPasswordAndVerificationToken(Restaurant restaurant, String randomPassword);

	void sendContectEmail(@Valid ContactDto contactDto);
}
