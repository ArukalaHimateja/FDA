package com.fda.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.RestaurantFilterWithPaginationDto;
import com.fda.app.dto.RestaurantRequestDto;
import com.fda.app.dto.RestaurantRequestFilterWithPaginationDto;
import com.fda.app.dto.RestaurantUpdateRequestDto;
import com.fda.app.service.IRestaurantService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class RestaurantController {
	@Autowired
	private IRestaurantService restaurantService;

	@RequestMapping(value = "/restaurant/add/request", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto addRestaurant(@Valid @RequestBody RestaurantRequestDto restaurantRequestDto) {
		ApiResponseDtoBuilder responseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.addRestaurant(restaurantRequestDto, responseDtoBuilder);
		return responseDtoBuilder.build();
	}
	@RequestMapping(value = "/restaurant/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto updateRestaurant(@Valid @RequestBody RestaurantUpdateRequestDto restaurantDto,
			@RequestParam(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.updateRestaurant(restaurantDto, id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/get/restaurant/request/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getRestaurantRequestById(@PathVariable long id) {
		ApiResponseDtoBuilder responseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.getRestaurantRequestById(id, responseDtoBuilder);
		return responseDtoBuilder.build();
	}
	@RequestMapping(value = "/get/restaurant/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getRestaurantById(@PathVariable long id) {
		ApiResponseDtoBuilder responseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.getRestaurantById(id, responseDtoBuilder);
		return responseDtoBuilder.build();
	}
	@RequestMapping(value = "/get/restaurant/request/document/{restaurantRequestId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getRestaurantDocumentByRestaurantRequestId(@PathVariable long restaurantRequestId) {
		ApiResponseDtoBuilder responseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.getRestaurantDocumentByRestaurantRequestId(restaurantRequestId, responseDtoBuilder);
		return responseDtoBuilder.build();
	}
	@RequestMapping(value = "/get/restaurant/request/{id}/document", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getRestaurantRequestDocumentById(@PathVariable long id) {
		ApiResponseDtoBuilder responseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.getRestaurantRequestDocumentById(id, responseDtoBuilder);
		return responseDtoBuilder.build();
	}
	@RequestMapping(value = "/admin/approved/restaurant/request/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto adminApprovedRestaurantById(@PathVariable long id) {
		ApiResponseDtoBuilder responseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.adminApprovedRestaurantById(id, responseDtoBuilder);
		return responseDtoBuilder.build();
	}

	@RequestMapping(value = "/admin/reject/restaurant/request/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto adminRejectRestaurantById(@PathVariable long id) {
		ApiResponseDtoBuilder responseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.adminRejectRestaurantById(id, responseDtoBuilder);
		return responseDtoBuilder.build();
	}

	@RequestMapping(value = "/get/all/restaurant", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getAllRestaurant() {
		ApiResponseDtoBuilder responseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.getAllRestaurant(responseDtoBuilder);
		return responseDtoBuilder.build();
	}

	@RequestMapping(value = "/get/user/restaurant/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getRestaurantByUserId(@PathVariable long userId) {
		ApiResponseDtoBuilder responseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.getRestaurantByUserId(userId, responseDtoBuilder);
		return responseDtoBuilder.build();
	}

	@RequestMapping(value = "/restaurant/active", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto isActiveRestaurant(@RequestParam(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.isActiveRestaurant(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/restaurant/inactive", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto isInactiveRestaurant(@RequestParam(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.isInactiveRestaurant(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/restaurant/search/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto searchRestaurant(@PathVariable String keyword) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.searchRestaurant(keyword, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/restaurant/pagination/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto searchRestaurantFilterWithPagination(
			@RequestBody RestaurantFilterWithPaginationDto filterWithPagination) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.searchRestaurantFilterWithPagination(filterWithPagination, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/get/all/admin/restaurant/request/pagination/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto getAllListOfRestaurantRequest( 	
			@RequestBody RestaurantRequestFilterWithPaginationDto restaurantRequestFilterWithPaginationDto) {
		ApiResponseDtoBuilder responseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.getAllListOfRestaurantRequest(restaurantRequestFilterWithPaginationDto, responseDtoBuilder);	
		return responseDtoBuilder.build();
	}

	@RequestMapping(value = "/get/all/admin/restaurant/pagination/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto getAllListOfRestaurant(
			@RequestBody RestaurantRequestFilterWithPaginationDto restaurantRequestFilterWithPaginationDto) {
		ApiResponseDtoBuilder responseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.getAllListOfRestaurant(restaurantRequestFilterWithPaginationDto, responseDtoBuilder);
		return responseDtoBuilder.build();
	}
}
