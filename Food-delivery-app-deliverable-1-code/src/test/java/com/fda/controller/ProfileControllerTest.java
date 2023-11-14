package com.fda.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.fda.app.FoodDeriveryAppApplication;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.UserUpdateRequestDto;
import com.fda.app.model.User;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = FoodDeriveryAppApplication.class)
public class ProfileControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private final String URL = "http://localhost:";

	@Test
	public void changePassword() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String url = URL + port + "/api/changePassword";
		long userId = 1l;
		String oldPassword = "string";
		String newPassword = "string";
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("userId", "{userId}")
				.queryParam("oldPassword", "{oldPassword}").queryParam("newPassword", "{newPassword}").encode()
				.toUriString();
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("oldPassword", oldPassword);
		params.put("newPassword", newPassword);
		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.POST,
				entity, ApiResponseDtoBuilder.class, params);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	public void deleteUserById() throws Exception {
		String url = URL + port + "/api/user/" + 1l + "/delete";

		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).encode().toUriString();

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.DELETE,
				null, ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

	}

	@Test
	public void updateUser() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		UserUpdateRequestDto user = new UserUpdateRequestDto();
		user.setFullName("Test");
		user.setEmail("anonymousUser");
		user.setMobileNumber("8888888888");

		String url = URL + port + "/api/customer/update/" + 1;
		HttpEntity<UserUpdateRequestDto> request = new HttpEntity<>(user, headers);

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.postForEntity(url, request,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

	}

	@Test
	public void updateUserAddress() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String address = "test";
		Long userId = 1l;
		String url = URL + port + "/api/user/address/update";
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("userId", "{userId}")
				.queryParam("address", "{address}").encode().toUriString();
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("address", address);
		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.POST,
				entity, ApiResponseDtoBuilder.class, params);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

	}

	@Test
	public void getUserDetailsById() throws Exception {
		String url = URL + port + "/api/user/" + 1;

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.getForEntity(url,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}
}
