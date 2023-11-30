package com.fda.app.service;

import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.CheckoutPayment;

@Service
public interface IPaymentService {

	void paymentForStripeProduct(CheckoutPayment checkoutPayment, ApiResponseDtoBuilder apiResponseDtoBuilder);

}
