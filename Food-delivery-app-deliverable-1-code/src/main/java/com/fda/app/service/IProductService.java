package com.fda.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.ProductDto;
import com.fda.app.dto.ProductFilterWithPaginationDto;
import com.fda.app.dto.ProductSearchFilterWithPaginationDto;
import com.fda.app.dto.ProductUpdateDto;
import com.fda.app.model.FoodProduct;

@Service
public interface IProductService {

	void addProduct(@Valid ProductDto fdaDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllProductListDetails(ApiResponseDtoBuilder apiResponseDtoBuilder);

	void save(FoodProduct product);

	void getProductById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void updateProduct( @Valid ProductUpdateDto productDto, long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void isActiveProduct(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void isInactiveProduct(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void productSearchFilterWithPagination(ProductSearchFilterWithPaginationDto productSearchFilterWithPaginationDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getProductListByFilterWithPagination(ProductFilterWithPaginationDto filterWithPagination,
			ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllProductListByRestaurantId(long restaurantId, ApiResponseDtoBuilder apiResponseDtoBuilder);


}
