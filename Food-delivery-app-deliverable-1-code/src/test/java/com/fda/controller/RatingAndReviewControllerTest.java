package com.fda.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fda.app.FoodDeriveryAppApplication;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.RatingAndReviewRequestDto;
import com.fda.app.model.User;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = FoodDeriveryAppApplication.class)
public class RatingAndReviewControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private final String URL = "http://localhost:";

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		user.setEmail("string");
		user.setFullName("string");
		user.setPassword("string");
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	@Test
	public void addRating() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		RatingAndReviewRequestDto ratingRequestDto = new RatingAndReviewRequestDto();
		ratingRequestDto.setRating(3);
		String url = URL + port + "/api/rating/add";
		HttpEntity<RatingAndReviewRequestDto> request = new HttpEntity<>(ratingRequestDto, headers);

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.postForEntity(url, request,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}

	@Test
	public void getRatingListByUserId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		long userId = 1l;
		String url = URL + port + "/api/user/" + userId + "/ratings";
		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.getForEntity(url,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
}
