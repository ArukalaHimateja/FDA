package com.fda.app.repository.custom;

import com.fda.app.dto.OrderFilterWithPaginationDto;
import com.fda.app.dto.PaginationDto;

public interface OrderRepositoryCustom {

	PaginationDto getOrderListByFilterWithPagination(OrderFilterWithPaginationDto orderFilterWithPaginationDto);

}
