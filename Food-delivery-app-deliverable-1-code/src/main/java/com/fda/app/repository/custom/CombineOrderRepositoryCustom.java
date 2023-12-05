package com.fda.app.repository.custom;

import com.fda.app.dto.CombineOrderFilterWithPaginationDto;
import com.fda.app.dto.OrderFilterWithPaginationDto;
import com.fda.app.dto.PaginationDto;

public interface CombineOrderRepositoryCustom {

	PaginationDto getCombineOrderListByFilterWithPagination(CombineOrderFilterWithPaginationDto orderFilterWithPaginationDto);

}
