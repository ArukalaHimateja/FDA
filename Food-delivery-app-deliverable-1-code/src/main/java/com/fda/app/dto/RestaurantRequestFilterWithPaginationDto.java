package com.fda.app.dto;

public class RestaurantRequestFilterWithPaginationDto {

	private RestaurantRequestFilterDto restaurantRequestFilterDto;

	private PaginationDto pagination;

	public PaginationDto getPagination() {
		return pagination;
	}

	public void setPagination(PaginationDto pagination) {
		this.pagination = pagination;
	}

	public RestaurantRequestFilterDto getRestaurantRequestFilterDto() {
		return restaurantRequestFilterDto;
	}

	public void setRestaurantRequestFilterDto(RestaurantRequestFilterDto restaurantRequestFilterDto) {
		this.restaurantRequestFilterDto = restaurantRequestFilterDto;
	}

}
