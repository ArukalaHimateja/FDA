package com.fda.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fda.app.model.RestaurantDocument;

@Repository
public interface RestaurantDocumentRepository extends JpaRepository<RestaurantDocument, Long> {

	List<RestaurantDocument> findByRestaurantRequestId(Long id);
	//List<RestaurantDocument> findByRestaurantRequestId(Long id);

}
