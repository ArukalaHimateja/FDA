package com.fda.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
import com.fda.app.service.IFeedbackService;

@Service
public class FeedbackServiceImpl implements IFeedbackService {
	@Autowired
	private FeedbackRepository feedbackRepository;
	@Autowired
	private CustomMapper customMapper;
	@Autowired
	private FeedbackRepositoryCustom feedbackRepositoryCustom;
	@Autowired
	private UserRepository userRepository;

	@Override
	public void addFeedback(@Valid FeedbackRequestDto feedbackRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<User> user = userRepository.findById(feedbackRequestDto.getUserId());
		if (user.isPresent()) {
			Feedback feedback = customMapper.feedbackRequestDtoToFeedback(feedbackRequestDto);
			feedback.setUserName(user.get().getFullName());
			feedback.setCreatedAt(new Date());
			feedbackRepository.save(feedback);
			apiResponseDtoBuilder.withMessage(Constants.FEEDBACK_SENT_SUCCESSFULLY).withStatus(HttpStatus.OK)
					.withData(feedback);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}

	}

	@Override
	public void getFeedbackListByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Feedback> feedbacks = feedbackRepository.findByUserId(userId);
		apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(feedbacks);
	}

	@Override
	public void getFeedbackListByFilterWithPagination(FeedbackFilterWithPaginationDto filterWithPagination,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		PaginationDto pagination = feedbackRepositoryCustom.getFeedbackListByFilterWithPagination(filterWithPagination);
		apiResponseDtoBuilder.withMessage(Constants.DATA_LIST).withStatus(HttpStatus.OK).withData(pagination);

	}

}
