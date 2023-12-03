package com.fda.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;

@Service
public interface IBillingService {

	void billingGenerate(@Valid long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getBillingById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

}
