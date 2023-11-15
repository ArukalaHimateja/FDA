package com.fda.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

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
import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.UserFilterDto;
import com.fda.app.dto.UserFilterWithPaginationDto;
import com.fda.app.dto.UserRequestDto;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = FoodDeriveryAppApplication.class)
public class UserControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private final String URL = "http://localhost:";

	//@Test
	public void addUser() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		UserRequestDto userRequestDto = new UserRequestDto();
		userRequestDto.setFullName("Test");
		userRequestDto.setEmail("anonymousUser");
		userRequestDto.setMobileNumber("8888888888");
		userRequestDto.setPassword("Test@123");
		userRequestDto.setAddress("test");

		String url = URL + port + "/api/user/add";
		HttpEntity<UserRequestDto> request = new HttpEntity<>(userRequestDto, headers);

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.postForEntity(url, request,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	//@Test
	public void forgotPassword() throws Exception {
		String url = URL + port + "/api/user/password/forgot";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String email = "test123@test.com";
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("email", "{email}").encode()
				.toUriString();
		Map<String, Object> params = new HashMap<>();
		params.put("email", email);
		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.POST,
				entity, ApiResponseDtoBuilder.class, params);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	public void getAllUsersListDetails() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String url = URL + port + "/api/user/list";
		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.getForEntity(url,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	}

	//@Test
	public void isActiveUser() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		long id = 1L;
		String url = URL + port + "/api/user/active";

		HttpEntity<?> entity = new HttpEntity<>(headers);
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("id", "{id}").encode().toUriString();
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.POST,
				entity, ApiResponseDtoBuilder.class, params);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	//@Test
	public void isInactiveUser() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		long id = 1L;
		String url = URL + port + "/api/user/inactive";

		HttpEntity<?> entity = new HttpEntity<>(headers);
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("id", "{id}").encode().toUriString();
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.POST,
				entity, ApiResponseDtoBuilder.class, params);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	//@Test
	public void getUserListByFilterWithPagination() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		UserFilterWithPaginationDto userFilterWithPaginationDto = new UserFilterWithPaginationDto();
		UserFilterDto userFilterDto = new UserFilterDto();
		PaginationDto PaginationDto = new PaginationDto();
		PaginationDto.setCurrentPage(1);
		PaginationDto.setPerPage(10);
		userFilterWithPaginationDto.setPagination(PaginationDto);
		userFilterWithPaginationDto.setFilter(userFilterDto);
		String url = URL + port + "/api/customer/pagination/filter";
		HttpEntity<UserFilterWithPaginationDto> request = new HttpEntity<>(userFilterWithPaginationDto, headers);

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.postForEntity(url, request,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

}