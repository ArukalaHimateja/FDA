package com.fda.app.service.impl;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.ReportDto;
import com.fda.app.dto.ReportFilterWithPaginationDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.Report;
import com.fda.app.model.User;
import com.fda.app.repository.ReportRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.repository.custom.ReportRepositoryCustom;
import com.fda.app.service.IReportService;
import com.fda.app.utility.Utility;
@Service
public class ReportServiceImpl implements IReportService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomMapper customMapper;
	@Autowired
	private ReportRepository reportRepository;
@Autowired
private ReportRepositoryCustom reportRepositoryCustom;
	@Override
	public void addReport(@Valid ReportDto reportDto, ApiResponseDtoBuilder apiResponseDtoBuilder) {

		User user = Utility.getSessionUser(userRepository);
		if ((user.getRole() == 0 || user.getRole() == 1)) {
			Report report = customMapper.reportDtoToReport(reportDto);
			report.setCreatedAt(new Date());
			report.setCustomerId(user.getId());
			reportRepository.save(report);
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(report);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}

	}

	@Override
	public void getReportById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Report> report=reportRepository.findById(id);
		if(report.isPresent()) {
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(report);	
		}else {
			apiResponseDtoBuilder.withMessage(Constants.REPORT_NOT_FOUND).withStatus(HttpStatus.UNAUTHORIZED);
		}
		
	}

	@Override
	public void getReportListByFilterWithPagination(ReportFilterWithPaginationDto filterWithPagination,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		PaginationDto pagination =reportRepositoryCustom.getReportListByFilterWithPagination(filterWithPagination);
		apiResponseDtoBuilder.withMessage(Constants.DATA_LIST).withStatus(HttpStatus.OK).withData(pagination);

		
	}

}
