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
import com.fda.app.dto.ContactDto;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = FoodDeriveryAppApplication.class)
public class BillingControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private final String URL = "http://localhost:";

	@Test
	public void billingGenerate() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Long orderId = 26l;
		String url = URL + port + "/api/billing/generate";
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("orderId", "{orderId}").encode()
				.toUriString();
		Map<String, Object> params = new HashMap<>();
		params.put("orderId", orderId);
		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.POST,
				entity, ApiResponseDtoBuilder.class, params);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	public void getBillingById() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String url = URL + port + "/api/get/billing/" + 26l;
		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.getForEntity(url,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}