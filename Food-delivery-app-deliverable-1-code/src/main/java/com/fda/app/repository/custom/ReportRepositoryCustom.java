package com.fda.app.repository.custom;

import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.ReportFilterWithPaginationDto;

public interface ReportRepositoryCustom {

	PaginationDto getReportListByFilterWithPagination(ReportFilterWithPaginationDto filterWithPagination);

}
