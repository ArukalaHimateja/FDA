package com.fda.app.dto;

public class CombineOrderFilterWithPaginationDto {

	private CombineOrderFilter filter;
	private PaginationDto pagination;

	public PaginationDto getPagination() {
		return pagination;
	}

	public void setPagination(PaginationDto pagination) {
		this.pagination = pagination;
	}

	public CombineOrderFilter getFilter() {
		return filter;
	}

	public void setFilter(CombineOrderFilter filter) {
		this.filter = filter;
	}
}
