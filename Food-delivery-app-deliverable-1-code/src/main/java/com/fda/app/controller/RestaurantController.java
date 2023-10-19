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
import com.fda.app.dto.RestaurantDto;
import com.fda.app.dto.RestaurantRequestDto;
import com.fda.app.service.IRestaurantService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class RestaurantController {
	@Autowired
	private IRestaurantService restaurantService;

	@RequestMapping(value = "/restaurant/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto addRestaurant(@RequestBody @Valid RestaurantRequestDto restaurantRequestDto) {
		ApiResponseDtoBuilder responseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.addRestaurant(restaurantRequestDto, responseDtoBuilder);
		return responseDtoBuilder.build();
	}

	@RequestMapping(value = "/restaurant/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto updateRestaurant(@Valid @RequestBody RestaurantDto restaurantDto,
			@RequestParam(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.updateRestaurant(restaurantDto, id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/get/restaurant/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getRestaurantById(@PathVariable long id) {
		ApiResponseDtoBuilder responseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.getRestaurantById(id, responseDtoBuilder);
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

	@RequestMapping(value = "/restaurant/search/{restaurantName}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto searchRestaurant(@PathVariable String restaurantName) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		restaurantService.searchRestaurant(restaurantName, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
}
