package com.fda.app.dto;

public class OrderFilterWithPaginationDto {

	private OrderFilter filter;
	private PaginationDto pagination;

	public PaginationDto getPagination() {
		return pagination;
	}

	public void setPagination(PaginationDto pagination) {
		this.pagination = pagination;
	}

	public OrderFilter getFilter() {
		return filter;
	}

	public void setFilter(OrderFilter filter) {
		this.filter = filter;
	}

}
