package com.fda.app.service;

import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;

@Service
public interface ILocationService {

	void searchRestaurant(String location, ApiResponseDtoBuilder apiResponseDtoBuilder);

}
