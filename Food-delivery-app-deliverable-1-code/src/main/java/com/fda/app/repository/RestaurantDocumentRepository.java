package com.fda.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fda.app.model.RestaurantDocument;

@Repository
public interface RestaurantDocumentRepository extends JpaRepository<RestaurantDocument, Long> {

	RestaurantDocument findByRestaurantRequestId(Long id);

}
