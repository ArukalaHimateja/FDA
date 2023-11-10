package com.fda.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.ContactDto;
import com.fda.app.service.IVerificationTokenService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class ContactController {
	@Autowired
	private IVerificationTokenService verificationTokenService;

	@RequestMapping(value = "/contact/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto addContact(@Valid @RequestBody ContactDto contactDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		new Thread(() -> {
			verificationTokenService.sendContectEmail(contactDto);
		}).start();
		apiResponseDtoBuilder.withMessage(Constants.CONTACT_REQUEST_SUCCESS).withStatus(HttpStatus.OK);
		return apiResponseDtoBuilder.build();
	}
}
