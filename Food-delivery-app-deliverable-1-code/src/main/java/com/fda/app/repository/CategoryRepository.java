package com.fda.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fda.app.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findByRestaurantId(long restaurantId);

	Optional<Category> findById(Long categoryId);

	boolean existsByNameAndRestaurantId(String name, Long restaurantId);

	boolean existsByName(String name);

}
