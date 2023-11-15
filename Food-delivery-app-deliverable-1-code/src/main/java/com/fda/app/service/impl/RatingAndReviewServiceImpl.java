package com.fda.app.service.impl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.RatingAndReviewRequestDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.RatingAndReview;
import com.fda.app.repository.RatingAndReviewRepository;
import com.fda.app.service.IRatingAndReviewService;

@Service
public class RatingAndReviewServiceImpl implements IRatingAndReviewService {
	@Autowired
	private RatingAndReviewRepository ratingRepository;
	@Autowired
	private CustomMapper customMapper;

	@Override
	public void addReview(@Valid RatingAndReviewRequestDto ratingRequestDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		RatingAndReview rating = customMapper.ratingRequestDtoToRating(ratingRequestDto);
		rating.setCreatedAt(new Date());
		ratingRepository.save(rating);
		apiResponseDtoBuilder.withMessage(Constants.RATING_ADD_SUCCESS).withStatus(HttpStatus.OK).withData(rating);
	}

	@Override
	public void getReviewListByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<RatingAndReview> ratings = ratingRepository.findByUserId(userId);
		apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(ratings);
	}

	@Override
	public void getReviewListByOrderId(long productId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<RatingAndReview> ratings = ratingRepository.findByProductId(productId);
		apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(ratings);

	}

}
