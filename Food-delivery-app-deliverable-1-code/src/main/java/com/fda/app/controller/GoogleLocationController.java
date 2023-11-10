package com.fda.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.service.IGoogleLocationService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = Constants.API_BASE_URL)
@Api(tags = "GoogleLocationController")
public class GoogleLocationController {

	@Autowired
	private IGoogleLocationService geolocationService;

	@RequestMapping(value = "/restaurant/glocation/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto searchGLoctionByRestaurantId(@PathVariable Long restaurantId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		geolocationService.searchGLoctionByRestaurantId(restaurantId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/restaurant/nearby/glocation/{centerLat}/{centerLon}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto searchGLoctionByNear(@PathVariable Double centerLat, @PathVariable Double centerLon) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		geolocationService.searchGLoctionByNear(centerLat, centerLon, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
}
