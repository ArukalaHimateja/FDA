package com.fda.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fda.app.model.FoodProduct;

@Repository
public interface FoodProductRepository extends JpaRepository<FoodProduct, Long> {

	boolean existsByproductName(String productName);

	List<FoodProduct> findByRestaurantId(long restaurantId);


}
