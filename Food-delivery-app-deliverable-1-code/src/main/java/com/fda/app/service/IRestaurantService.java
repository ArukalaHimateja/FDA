package com.fda.app.service;

import javax.validation.Valid;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.RestaurantFilterWithPaginationDto;
import com.fda.app.dto.RestaurantRequestDto;
import com.fda.app.dto.RestaurantRequestFilterWithPaginationDto;
import com.fda.app.dto.RestaurantUpdateRequestDto;
import com.fda.app.model.Restaurant;

public interface IRestaurantService {
	void save(Restaurant restaurant);

	void getRestaurantById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllRestaurant(ApiResponseDtoBuilder responseDtoBuilder);

	void getRestaurantByUserId(long userId, ApiResponseDtoBuilder responseDtoBuilder);

	void updateRestaurant(@Valid RestaurantUpdateRequestDto restaurantDto, long id,
			ApiResponseDtoBuilder apiResponseDtoBuilder);

	void searchRestaurant(String restaurantName, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void addRestaurant(@Valid RestaurantRequestDto restaurantRequestDto, ApiResponseDtoBuilder responseDtoBuilder);

	void adminApprovedRestaurantById(long id, ApiResponseDtoBuilder responseDtoBuilder);

	void adminRejectRestaurantById(long id, ApiResponseDtoBuilder responseDtoBuilder);

	void searchRestaurantFilterWithPagination(RestaurantFilterWithPaginationDto filterWithPagination,
			ApiResponseDtoBuilder apiResponseDtoBuilder);

	void isActiveRestaurant(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void isInactiveRestaurant(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllListOfRestaurantRequest(
			RestaurantRequestFilterWithPaginationDto restaurantRequestFilterWithPaginationDto,
			ApiResponseDtoBuilder responseDtoBuilder);

	void getAllListOfRestaurant(RestaurantRequestFilterWithPaginationDto restaurantFilterWithPaginationDto,
			ApiResponseDtoBuilder responseDtoBuilder);

	void getRestaurantRequestById(long id, ApiResponseDtoBuilder responseDtoBuilder);

	void getRestaurantRequestDocumentById(long id, ApiResponseDtoBuilder responseDtoBuilder);

	void getRestaurantDocumentByRestaurantRequestId(long restaurantRequestId, ApiResponseDtoBuilder responseDtoBuilder);
}
