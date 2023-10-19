package com.fda.app.repository.custom;

import java.util.List;

import com.fda.app.model.Restaurant;

public interface RestaurantRepositoryCustom {

	List<Restaurant> getRestaurantListByFilterWithPagination(String restaurantName);

}
