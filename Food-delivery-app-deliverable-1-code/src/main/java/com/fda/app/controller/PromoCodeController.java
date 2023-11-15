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
import com.fda.app.dto.PromoCodeFilterWithPaginationDto;
import com.fda.app.dto.PromoCodeRequestDto;
import com.fda.app.service.IPromoCodeService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class PromoCodeController {

	@Autowired
	private IPromoCodeService promoCodeService;

	@RequestMapping(value = "/promoCode/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto addPromoCode(@Valid @RequestBody PromoCodeRequestDto promoCodeRequestDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
			promoCodeService.addPromoCode(promoCodeRequestDto, apiResponseDtoBuilder);
	
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/promoCodes/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getPromoCodeById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
			promoCodeService.getPromoCodeById(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/promoCode/{promoCode}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getPromoCodeByName(@PathVariable(required = true) String promoCode) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
			promoCodeService.getPromoCodeByName(promoCode, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

//	@RequestMapping(value = "/promoCode/apply/{promoCode}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
//	public ApiResponseDto promoCodeApplyByPromoCode(@PathVariable(required = true) String promoCode) {
//		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
//		if (posAuthService.validateCookies(request, apiResponseDtoBuilder)) {
//		promoCodeService.promoCodeApplyByPromoCode(promoCode, apiResponseDtoBuilder);
//		}
//		return apiResponseDtoBuilder.build();
//	}
	@RequestMapping(value = "/promoCodes/pagination/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto getpromoCodesListByFilterWithPagination(
			@RequestBody PromoCodeFilterWithPaginationDto filterWithPagination) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		promoCodeService.getPromoCodesListByFilterWithPagination(filterWithPagination, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@RequestMapping(value = "/promoCode/isValid", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto applyPromoCode(@RequestParam(required = true) String promoCode) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
			promoCodeService.isPromocodeValid(promoCode, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
}
