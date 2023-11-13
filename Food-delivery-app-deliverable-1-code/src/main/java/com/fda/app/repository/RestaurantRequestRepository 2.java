package com.fda.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fda.app.model.RestaurantRequest;

@Repository
public interface RestaurantRequestRepository extends JpaRepository<RestaurantRequest, Long>{

	List<RestaurantRequest> findAllByStatus(int i);

	boolean existsByEmail(String email);

	RestaurantRequest findByEmail(String email);

}
