package com.fda.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.User;
import com.fda.app.model.VerificationToken;
import com.fda.app.repository.VerificationTokenRepository;
import com.fda.app.service.IUserService;
import com.fda.app.service.impl.VerificationTokenServiceImpl;

@ExtendWith(MockitoExtension.class)
public class VerificationTokenServiceImplTest {
	@InjectMocks
	VerificationTokenServiceImpl verificationTokenServiceImpl;
	@Mock
	VerificationTokenRepository verificationTokenRepository;

	@Mock
	IUserService userService;
	@Mock
	CustomMapper customMapper;

	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";
	public static final String TOKEN_VALID = "valid";

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@SuppressWarnings("null")
	@Test
	public void validateToken() {
		String token = "test";
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);

		Date expiryDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(expiryDate);
		cal.add(Calendar.DATE, 1);
		expiryDate = cal.getTime();
		verificationToken.setExpiryDate(expiryDate);
		verificationToken.setUser(23L);
		when(verificationTokenRepository.findByToken(token)).thenReturn(verificationToken);
		User user = new User();
		user.setRole(4);
		when(userService.findById(verificationToken.getUser())).thenReturn(user);
		String results = verificationTokenServiceImpl.validateToken(token);
		assertTrue(results.equals("Thank you for verify your email!!"));
	}

	@Test
	public void resendRegistrationToken() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		long id = 1L;
		User user = new User();
		user.setId(1L);
		when(userService.findById(id)).thenReturn(user);
		verificationTokenServiceImpl.resendRegistrationToken(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.CONFIRMATION_EMAIL_SENT));

	}

}
