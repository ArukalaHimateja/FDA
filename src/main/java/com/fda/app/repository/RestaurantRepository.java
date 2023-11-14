package com.fda.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fda.app.model.Restaurant;
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	Optional<Restaurant> findByUserId(long userId);

	boolean existsByUserId(long id);

	boolean existsByRestaurantEmail(String restaurantEmail);

	List<Restaurant> findAllByStatus(int i);

	Restaurant findByRestaurantEmail(String restaurantEmail);

	
}
