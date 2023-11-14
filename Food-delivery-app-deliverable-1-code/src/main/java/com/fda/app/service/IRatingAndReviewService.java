package com.fda.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.RatingAndReviewRequestDto;

@Service
public interface IRatingAndReviewService {

	void addReview(@Valid RatingAndReviewRequestDto ratingRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getReviewListByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getReviewListByOrderId(long productId, ApiResponseDtoBuilder apiResponseDtoBuilder);

}
