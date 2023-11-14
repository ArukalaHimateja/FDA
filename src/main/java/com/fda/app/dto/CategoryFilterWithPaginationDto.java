package com.fda.app.dto;

public class CategoryFilterWithPaginationDto {
	private CategoryFilterDto categoryFilterDto;
	private PaginationDto pagination;

	public CategoryFilterDto getCategoryFilterDto() {
		return categoryFilterDto;
	}

	public void setCategoryFilterDto(CategoryFilterDto categoryFilterDto) {
		this.categoryFilterDto = categoryFilterDto;
	}

	public PaginationDto getPagination() {
		return pagination;
	}

	public void setPagination(PaginationDto pagination) {
		this.pagination = pagination;
	}

}
