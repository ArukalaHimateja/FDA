package com.fda.app.repository.custom;

import com.fda.app.dto.CategoryFilterWithPaginationDto;
import com.fda.app.dto.PaginationDto;

public interface CategoryRepositoryCustom {

	PaginationDto getCategoryListByFilterWithPagination(
			CategoryFilterWithPaginationDto categoryFilterWithPaginationDto);

}
