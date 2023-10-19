package com.fda.app.dto;

public class RestaurantRequestDto {
	private RestaurantDto restaurantDto;
	private UserRequestDto userRequestDto;

	public RestaurantDto getRestaurantDto() {
		return restaurantDto;
	}

	public void setRestaurantDto(RestaurantDto restaurantDto) {
		this.restaurantDto = restaurantDto;
	}

	public UserRequestDto getUserRequestDto() {
		return userRequestDto;
	}

	public void setUserRequestDto(UserRequestDto userRequestDto) {
		this.userRequestDto = userRequestDto;
	}

}
