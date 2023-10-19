package com.fda.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fda.app.model.Restaurant;
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	Optional<Restaurant> findByUserId(long userId);

	boolean existsByUserId(long id);

	boolean existsByRestaurantGstNo(String restaurantGstNo);

	boolean existsByOwnerAadharNumber(String ownerAadharNumber);
	
}
