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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.RatingAndReviewRequestDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.RatingAndReview;
import com.fda.app.model.User;
import com.fda.app.repository.RatingAndReviewRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.service.impl.RatingAndReviewServiceImpl;
import com.fda.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class RatingAndReviewServiceImplTest {
	@InjectMocks
	RatingAndReviewServiceImpl ratingServiceImpl;
	@Mock
	RatingAndReviewRepository ratingRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	CustomMapper customMapper;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addRating() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		RatingAndReviewRequestDto ratingRequestDto = new RatingAndReviewRequestDto();
		ratingRequestDto.setRating(2);
		RatingAndReview rating = new RatingAndReview();
		rating.setId(1l);
		User user = new User();
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		user.setRole(0);
		user.setId(1l);
		when(Utility.getSessionUser(userRepository)).thenReturn(user);
		Optional<User> userOptional = Optional.of(user);
		when(userRepository.findById(1l)).thenReturn(userOptional);
		when(customMapper.ratingRequestDtoToRating(ratingRequestDto)).thenReturn(rating);
		ratingServiceImpl.addReview(ratingRequestDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.RATING_ADD_SUCCESS));

	}

	@Test
	public void getRatingListByUserId() {
		long userId = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		RatingAndReviewRequestDto ratingRequestDto = new RatingAndReviewRequestDto();
		ratingRequestDto.setRating(2);
		List<RatingAndReview> ratings = new ArrayList<>();
		when(ratingRepository.findByUserId(userId)).thenReturn(ratings);
		ratingServiceImpl.getReviewListByUserId(userId, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESS));

	}

	@Test
	public void getRatingListByProductId() {
		long productId = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		RatingAndReviewRequestDto ratingRequestDto = new RatingAndReviewRequestDto();
		ratingRequestDto.setRating(2);
		List<RatingAndReview> ratings = new ArrayList<>();
		when(ratingRepository.findByProductId(productId)).thenReturn(ratings);
		ratingServiceImpl.getReviewListByProductId(productId, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESS));

	}
}
