package com.fda.app.service;

import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;

@Service
public interface IGoogleLocationService {

	void searchGLoctionByRestaurantId(Long restaurantId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void searchGLoctionByNear(Double centerLat, Double centerLon, ApiResponseDtoBuilder apiResponseDtoBuilder);
}
