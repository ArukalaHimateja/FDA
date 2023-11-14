package com.fda.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.ReportDto;
import com.fda.app.dto.ReportFilterWithPaginationDto;

@Service
public interface IReportService {

	void addReport(@Valid ReportDto reportDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getReportById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getReportListByFilterWithPagination(ReportFilterWithPaginationDto filterWithPagination,
			ApiResponseDtoBuilder apiResponseDtoBuilder);

}
