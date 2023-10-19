package com.fda.app.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.model.Restaurant;
import com.fda.app.model.User;
import com.fda.app.model.VerificationToken;
import com.fda.app.repository.VerificationTokenRepository;
import com.fda.app.service.IEmailService;
import com.fda.app.service.IUserService;
import com.fda.app.service.IVerificationTokenService;

@Service
public class VerificationTokenServiceImpl implements IVerificationTokenService {

	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";
	public static final String TOKEN_VALID = "valid";

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	@Autowired
	private Environment environment;

	@Autowired
	private IUserService userService;

	@Autowired
	private IEmailService emailService;

	@Override
	public String validateToken(String token) {
		final String result = validateVerificationToken(token);
		if (result.equals(TOKEN_VALID)) {

			return "Thank you for verify your email!!";
		} else {

			return "Try again";
		}
	}

	public String validateVerificationToken(String token) {
		final VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
		if (verificationToken == null) {
			return TOKEN_INVALID;
		}

		final User user = userService.findById(verificationToken.getUser());
		final Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			verificationTokenRepository.delete(verificationToken);
			return TOKEN_EXPIRED;
		}

		user.setVerify(true);
		userService.save(user);
		return TOKEN_VALID;
	}

	@Override
	public void resendRegistrationToken(Long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User user = userService.findById(id);
		if (user == null) {
			apiResponseDtoBuilder.withMessage(Constants.USER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
			return;
		}
		sendVerificationToken(user);
		apiResponseDtoBuilder.withMessage(Constants.CONFIRMATION_EMAIL_SENT).withStatus(HttpStatus.OK);
	}

	@Override
	public void sendVerificationToken(User user) {
		new Thread(() -> {
			String subject = "FDA Account verification";
			String body = createEmailBody(user.getFullName(), registrationConfirmUrl(user.getId()));
			emailService.sendEmail(user.getEmail(), subject, body, "", null, null);
		}).start();
	}

	public String createEmailBody(String name, String url) {
		final String body = "<html><body><h3>Hello " + name.toUpperCase() + "</h3>"
				+ "<p>You registered an account on FDA<b>"
				+ "</b>, before being able to use your account you need to verify that this is your email verification by clicking here</p>"
				+ "<a href=\"" + url + "\">Clicking Here </a>" + "<br><br><p>Kind Regards,<br>Team FDA</body></html>";
		return body;
	}

	public String registrationConfirmUrl(Long userId) {
		VerificationToken token = createVerificationToken(userId);
		String url = environment.getProperty(Constants.SERVER_DOMAIN_URL) + Constants.API_BASE_URL
				+ "/registrationConfirm?token=" + token.getToken();
		return url;
	}

	private VerificationToken createVerificationToken(Long userId) {
		VerificationToken vToken = verificationTokenRepository.findByUser(userId);
		if (vToken == null) {
			vToken = new VerificationToken();
			vToken.setUser(userId);
			vToken.setCreatedAt(new Date());
		}
		vToken.updateToken(UUID.randomUUID().toString().toUpperCase());
		verificationTokenRepository.save(vToken);
		return vToken;
	}
	@Override
	public void sendRestaurantVerificationToken(Restaurant restaurant) {
		new Thread(() -> {
			String subject = "fda Account verification";
			String body = createEmailBody(restaurant.getRestaurantName(), registrationConfirmUrl(restaurant.getUserId()));
			emailService.sendEmail(restaurant.getRestaurantEmail(), subject, body, "", null, null);
		}).start();
		
	}
}
