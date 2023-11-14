package com.fda.app.repository.custom;

import java.util.List;

import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.RestaurantFilterWithPaginationDto;
import com.fda.app.dto.RestaurantRequestFilterWithPaginationDto;
import com.fda.app.model.Restaurant;

public interface RestaurantRepositoryCustom {

	List<Restaurant> getRestaurantListByRestaurantName(String restaurantName);

	PaginationDto getRestaurantListByFilterWithPagination(RestaurantFilterWithPaginationDto filterWithPagination);

	PaginationDto getRestaurantListByFilterWithPagination(
			RestaurantRequestFilterWithPaginationDto restaurantFilterWithPaginationDto);

}
