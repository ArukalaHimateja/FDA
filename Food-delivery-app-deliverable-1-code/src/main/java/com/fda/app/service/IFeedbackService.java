package com.fda.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.FeedbackFilterWithPaginationDto;
import com.fda.app.dto.FeedbackRequestDto;

@Service
public interface IFeedbackService {

	void addFeedback(@Valid FeedbackRequestDto feedbackRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getFeedbackListByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getFeedbackListByFilterWithPagination(FeedbackFilterWithPaginationDto filterWithPagination,
			ApiResponseDtoBuilder apiResponseDtoBuilder);

}
