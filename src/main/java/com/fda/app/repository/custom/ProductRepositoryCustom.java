package com.fda.app.repository.custom;

import java.util.List;

import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.ProductFilterWithPaginationDto;
import com.fda.app.dto.ProductSearchFilterResponseDto;
import com.fda.app.dto.ProductSearchFilterWithPaginationDto;

public interface ProductRepositoryCustom {

	PaginationDto getproductListByFilterWithPagination(ProductFilterWithPaginationDto filterWithPagination);

	List<ProductSearchFilterResponseDto> getproductSearchListByFilterWithPagination(
			ProductSearchFilterWithPaginationDto productSearchFilterWithPaginationDto);

}
