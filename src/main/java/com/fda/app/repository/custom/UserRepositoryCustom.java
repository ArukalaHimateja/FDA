package com.fda.app.repository.custom;

import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.UserFilterWithPaginationDto;

public interface UserRepositoryCustom {

	PaginationDto getCustomerListByFilterWithPagination(UserFilterWithPaginationDto filterWithPagination);
}
