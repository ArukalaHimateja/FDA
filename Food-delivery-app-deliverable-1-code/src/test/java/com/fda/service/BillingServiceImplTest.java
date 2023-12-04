package com.fda.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.Billing;
import com.fda.app.model.FoodProduct;
import com.fda.app.model.Order;
import com.fda.app.model.User;
import com.fda.app.repository.BillingRepository;
import com.fda.app.repository.FoodProductRepository;
import com.fda.app.repository.OrderRepository;
import com.fda.app.service.impl.BillingServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BillingServiceImplTest {
	@Mock
	CustomMapper customMapper;
	@InjectMocks
	private BillingServiceImpl billingServiceImpl;
	@Mock
	OrderRepository orderRepository;
	@Mock
	FoodProductRepository productRepository;
	@Mock
	BillingRepository billingRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void billingGenerate() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Long orderId = 1l;
		Order order = new Order();
		order.setId(1l);
		order.setProductId(1l);
		Optional<Order> orderOptional = Optional.of(order);
		when(orderRepository.findById(orderId)).thenReturn(orderOptional);
		FoodProduct foodProduct = new FoodProduct();
		foodProduct.setId(1l);
		Optional<FoodProduct> productOptional = Optional.of(foodProduct);
		when(productRepository.findById(orderOptional.get().getProductId())).thenReturn(productOptional);
		billingServiceImpl.billingGenerate(orderId, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.ORDER_PAY_PENDING));

	}

	@Test
	public void getBillingById() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Long id = 1l;
		Billing billing = new Billing();
		billing.setOrderId(1l);
		Optional<Billing> billingOptional = Optional.of(billing);
		when(billingRepository.findById(id)).thenReturn(billingOptional);
		billingServiceImpl.getBillingById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESS));

	}
}
