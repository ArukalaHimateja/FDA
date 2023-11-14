package com.fda.app.repository.custom;

import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.RestaurantRequestFilterWithPaginationDto;

public interface RestaurantRequestRepositoryCustom {

	PaginationDto getRestaurantRequestListByFilterWithPagination(
			RestaurantRequestFilterWithPaginationDto restaurantRequestFilterWithPaginationDto);

}
