package com.fda.app.dto;

public class ProductFilterWithPaginationDto {
	private ProductFilterDto filter;
	private PaginationDto pagination;

	public ProductFilterDto getFilter() {
		return filter;
	}

	public void setFilter(ProductFilterDto filter) {
		this.filter = filter;
	}

	public PaginationDto getPagination() {
		return pagination;
	}

	public void setPagination(PaginationDto pagination) {
		this.pagination = pagination;
	}

}
