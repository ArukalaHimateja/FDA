package com.fda.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.FeedbackFilterWithPaginationDto;
import com.fda.app.dto.FeedbackRequestDto;
import com.fda.app.dto.PaginationDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.Feedback;
import com.fda.app.model.User;
import com.fda.app.repository.FeedbackRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.repository.custom.FeedbackRepositoryCustom;
import com.fda.app.service.impl.FeedbackServiceImpl;
import com.fda.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceImplTest {
	@InjectMocks
	FeedbackServiceImpl feedbackServiceImpl;
	@Mock
	UserRepository userRepository;
	@Mock
	FeedbackRepository feedbackRepository;
	@Mock
	CustomMapper customMapper;
	@Mock
	private FeedbackRepositoryCustom feedbackRepositoryCustom;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addFeedback() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		FeedbackRequestDto feedbackRequestDto = new FeedbackRequestDto();
		feedbackRequestDto.setFeedback("test");
		Feedback feedback = new Feedback();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		Optional<User> optional = Optional.of(sessionUser);
		when(userRepository.findById(feedbackRequestDto.getUserId())).thenReturn(optional);
		when(customMapper.feedbackRequestDtoToFeedback(feedbackRequestDto)).thenReturn(feedback);
		feedbackServiceImpl.addFeedback(feedbackRequestDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.FEEDBACK_SENT_SUCCESSFULLY));

	}

	@Test
	public void getFeedbackListByUserId() {
		long userId = 1L;
		List<Feedback> feedbacks = new ArrayList<Feedback>();
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		when(feedbackRepository.findByUserId(userId)).thenReturn(feedbacks);
		feedbackServiceImpl.getFeedbackListByUserId(userId, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESS));

	}

	@Test
	public void getFeedbackListByFilterWithPagination() {
		long id = 1;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		FeedbackFilterWithPaginationDto feedbackFilterWithPaginationDto = new FeedbackFilterWithPaginationDto();
		PaginationDto pagination = new PaginationDto();
		pagination.setCurrentPage(0);
		pagination.setPerPage(10);
		when(feedbackRepositoryCustom.getFeedbackListByFilterWithPagination(feedbackFilterWithPaginationDto))
				.thenReturn(pagination);
		feedbackServiceImpl.getFeedbackListByFilterWithPagination(feedbackFilterWithPaginationDto,
				apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.DATA_LIST));
	}

}
