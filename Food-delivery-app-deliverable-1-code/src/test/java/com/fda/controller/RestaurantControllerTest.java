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
import com.fda.app.dto.CategoryRequestDto;
import com.fda.app.dto.RestaurantRequestDto;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = FoodDeriveryAppApplication.class)
public class RestaurantControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private final String URL = "http://localhost:";

	@Test
	public void addRestaurant() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto();
		restaurantRequestDto.setEmail("test");
		restaurantRequestDto.setMobileNumber("1234567899");
		restaurantRequestDto.setOwnerName("test");
		restaurantRequestDto.setRestaurantAddress("usa");
		restaurantRequestDto.setRestaurantLicenseNumber("123");
		restaurantRequestDto.setRestaurantName("test");

		String url = URL + port + "/api/restaurant/add/request";
		HttpEntity<RestaurantRequestDto> request = new HttpEntity<>(restaurantRequestDto, headers);

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.postForEntity(url, request,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	public void getRestaurantById() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String url = URL + port + "/api/get/restaurant/" + 1l;
		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.getForEntity(url,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void getRestaurantByUserId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String url = URL + port + "/api/get/user/restaurant/" + 1l;
		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.getForEntity(url,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void isActiveRestaurant() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		long id = 1L;
		String url = URL + port + "/api/restaurant/active";

		HttpEntity<?> entity = new HttpEntity<>(headers);
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("id", "{id}").encode().toUriString();
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.POST,
				entity, ApiResponseDtoBuilder.class, params);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	public void isInactiveRestaurant() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		long id = 1L;
		String url = URL + port + "/api/restaurant/inactive";

		HttpEntity<?> entity = new HttpEntity<>(headers);
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("id", "{id}").encode().toUriString();
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.POST,
				entity, ApiResponseDtoBuilder.class, params);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}
}