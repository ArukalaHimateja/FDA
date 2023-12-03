package com.fda.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.PromoCodeFilterWithPaginationDto;
import com.fda.app.dto.PromoCodeRequestDto;

@Service
public interface IPromoCodeService {

	void addPromoCode(@Valid PromoCodeRequestDto promoCodeRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getPromoCodeById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void isPromocodeValid(String promoCode, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getPromoCodeByName(String name, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getPromoCodesListByFilterWithPagination(PromoCodeFilterWithPaginationDto filterWithPagination,
			ApiResponseDtoBuilder apiResponseDtoBuilder);
}
