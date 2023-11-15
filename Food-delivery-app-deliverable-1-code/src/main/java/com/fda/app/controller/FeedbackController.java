package com.fda.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.FeedbackFilterWithPaginationDto;
import com.fda.app.dto.FeedbackRequestDto;
import com.fda.app.service.IFeedbackService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class FeedbackController {

	@Autowired
	private IFeedbackService feedbackService;

	@RequestMapping(value = "/feedback/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto addFeedback(@Valid @RequestBody FeedbackRequestDto feedbackRequestDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		feedbackService.addFeedback(feedbackRequestDto, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/user/{userId}/feedback", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getFeedbackListByUserId(@PathVariable(required = true) long userId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		feedbackService.getFeedbackListByUserId(userId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/feedback/pagination/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto getFeedbackListByFilterWithPagination(
			@RequestBody FeedbackFilterWithPaginationDto filterWithPagination) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		feedbackService.getFeedbackListByFilterWithPagination(filterWithPagination, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
}
