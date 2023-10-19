package com.fda.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.RestaurantDto;
import com.fda.app.dto.RestaurantRequestDto;
import com.fda.app.model.Restaurant;
@Service
public interface IRestaurantService {
	void save(Restaurant restaurant);

	void getRestaurantById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllRestaurant(ApiResponseDtoBuilder responseDtoBuilder);

	void getRestaurantByUserId(long userId, ApiResponseDtoBuilder responseDtoBuilder);

	void updateRestaurant(@Valid RestaurantDto restaurantDto, long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void searchRestaurant(String restaurantName, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void addRestaurant(@Valid RestaurantRequestDto restaurantRequestDto, ApiResponseDtoBuilder responseDtoBuilder);
}
