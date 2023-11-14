package com.fda.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.CategoryFilterWithPaginationDto;
import com.fda.app.dto.CategoryRequestDto;
import com.fda.app.model.Category;

@Service
public interface ICategoryService {

	void addCategory(@Valid CategoryRequestDto categoryRequestDto, ApiResponseDtoBuilder responseDtoBuilder);

	void getCategoryDetailsById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllCategoryListByRestaurantId(long restaurantId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void isActiveCategory(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void isInactiveCategory(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void deleteCategoryById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getCategoryListByFilterWithPagination(CategoryFilterWithPaginationDto categoryFilterWithPaginationDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder);

	void updateCategory(@Valid CategoryRequestDto categoryRequestDto, long id,
			ApiResponseDtoBuilder apiResponseDtoBuilder);

}
