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
import com.fda.app.dto.CategoryFilterWithPaginationDto;
import com.fda.app.dto.CategoryRequestDto;
import com.fda.app.service.ICategoryService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class CategoryController {
	@Autowired
	private ICategoryService categoryService;

	@RequestMapping(value = "/category/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto addCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
		ApiResponseDtoBuilder responseDtoBuilder = new ApiResponseDtoBuilder();
		categoryService.addCategory(categoryRequestDto, responseDtoBuilder);
		return responseDtoBuilder.build();
	}

	@RequestMapping(value = "/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getCategoryDetailsById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		categoryService.getCategoryDetailsById(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/category/list/by/restaurant/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getAllCategoryListByRestaurantId(@PathVariable(required = true) long restaurantId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		categoryService.getAllCategoryListByRestaurantId(restaurantId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/category/active", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto isActiveCategory(@RequestParam(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		categoryService.isActiveCategory(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/category/inactive", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto isInactiveCategory(@RequestParam(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		categoryService.isInactiveCategory(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/category/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public ApiResponseDto deleteCategoryById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		categoryService.deleteCategoryById(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/category/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto updateCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto,
			@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		categoryService.updateCategory(categoryRequestDto, id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/category/pagination/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto getCategoryListByFilterWithPagination(
			@RequestBody CategoryFilterWithPaginationDto categoryFilterWithPaginationDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		categoryService.getCategoryListByFilterWithPagination(categoryFilterWithPaginationDto, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
}
