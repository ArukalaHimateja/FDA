package com.fda.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fda.app.model.RatingAndReview;

@Repository
public interface RatingAndReviewRepository extends JpaRepository<RatingAndReview, Long> {

	List<RatingAndReview> findByUserId(long userId);

	List<RatingAndReview> findByProductId(long productId);

}
