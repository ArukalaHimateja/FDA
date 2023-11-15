package com.fda.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.ReportDto;
import com.fda.app.dto.ReportFilterWithPaginationDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.Report;
import com.fda.app.model.User;
import com.fda.app.repository.RatingAndReviewRepository;
import com.fda.app.repository.ReportRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.repository.custom.ReportRepositoryCustom;
import com.fda.app.service.impl.ReportServiceImpl;
import com.fda.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {
	@InjectMocks
	ReportServiceImpl reportServiceImpl;
	@Mock
	UserRepository userRepository;
	@Mock
	CustomMapper customMapper;
	@Mock
	private ReportRepository reportRepository;
	@Mock
	private ReportRepositoryCustom reportRepositoryCustom;
	@Mock
	private RatingAndReviewRepository ratingRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addReport() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();

		ReportDto userDto = new ReportDto();
		userDto.setEmail("testadmin@gmail.com");
		userDto.setName("Test Admin");
		userDto.setMobileNumber("11111111");
		userDto.setMessage("test");
		User user = new User();
		String email = "test123@gmail.com";
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		user.setRole(0);
		when(Utility.getSessionUser(userRepository)).thenReturn(user);
		Report report = new Report();
		when(customMapper.reportDtoToReport(userDto)).thenReturn(report);
		reportServiceImpl.addReport(userDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESS));

	}

	@Test
	public void getReportById() {
		long id = 3L;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Report report = new Report();
		report.setEmail("testseller@gmail.com");
		report.setMobileNumber("9999999999");
		report.setId(id);
		Optional<Report> optional = Optional.of(report);
		// Optional<Report> report=reportRepository.findById(id);
		when(reportRepository.findById(id)).thenReturn(optional);
		assertTrue(optional.isPresent());
		reportServiceImpl.getReportById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESS));
	}

	@Test
	public void getReportListByFilterWithPagination() {
		long id = 1;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		ReportFilterWithPaginationDto reportFilterWithPaginationDto = new ReportFilterWithPaginationDto();
		PaginationDto pagination = new PaginationDto();
		when(reportRepositoryCustom.getReportListByFilterWithPagination(reportFilterWithPaginationDto))
				.thenReturn(pagination);
		reportServiceImpl.getReportListByFilterWithPagination(reportFilterWithPaginationDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.DATA_LIST));
	}

}
