package com.fda.app.repository.custom;

import com.fda.app.dto.FeedbackFilterWithPaginationDto;
import com.fda.app.dto.PaginationDto;

public interface FeedbackRepositoryCustom {

	PaginationDto getFeedbackListByFilterWithPagination(FeedbackFilterWithPaginationDto filterWithPagination);

}
