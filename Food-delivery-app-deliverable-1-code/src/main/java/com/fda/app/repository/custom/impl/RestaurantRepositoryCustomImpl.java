package com.fda.app.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.fda.app.model.Restaurant;
import com.fda.app.repository.custom.RestaurantRepositoryCustom;

@Repository
public class RestaurantRepositoryCustomImpl implements RestaurantRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Restaurant> getRestaurantListByFilterWithPagination(String restaurantName) {
		String query = "SELECT t.* from restaurant_details t";
		String addableQuery = " where t.restaurant_name like '%" + restaurantName + "%'";
		Query queryString = entityManager.createNativeQuery(query + addableQuery, Restaurant.class);
		List<Restaurant> customerList = queryString.getResultList();
		return customerList;
	}

}
