package com.fda.app.repository.custom;

import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.PromoCodeFilterWithPaginationDto;

public interface PromoCodeRepositoyCustom {

	PaginationDto getPromoCodesListByFilterWithPagination(PromoCodeFilterWithPaginationDto filterWithPagination);

}
