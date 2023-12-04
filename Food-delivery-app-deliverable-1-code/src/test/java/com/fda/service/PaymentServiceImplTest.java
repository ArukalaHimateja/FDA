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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.CheckoutPayment;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.CombineOrder;
import com.fda.app.model.FoodProduct;
import com.fda.app.model.Order;
import com.fda.app.model.User;
import com.fda.app.repository.CombineOrderRepository;
import com.fda.app.repository.FoodProductRepository;
import com.fda.app.repository.OrderRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.repository.custom.UserRepositoryCustom;
import com.fda.app.service.IEmailService;
import com.fda.app.service.IVerificationTokenService;
import com.fda.app.service.impl.PaymentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
	@Mock
	CustomMapper customMapper;

	@InjectMocks
	private PaymentServiceImpl paymentServiceImpl;

	@Mock
	private OrderRepository orderRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	private IEmailService emailService;
	@Mock
	private FoodProductRepository foodProductRepository;
	@Mock
	private IVerificationTokenService verificationTokenService;
	@Mock
	private UserRepositoryCustom userRepositoryCustom;
	@Mock
	private CombineOrderRepository combineOrderRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void paymentForStripeProduct() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		CheckoutPayment checkoutPayment = new CheckoutPayment();
		checkoutPayment.setCancelUrl("test");
		checkoutPayment.setOrderId(1l);
		checkoutPayment.setSuccessUrl("test");
		CombineOrder combineOrder = new CombineOrder();
		combineOrder.setId(1l);
		combineOrder.setCustomerId(1l);
		Optional<CombineOrder> combineOrderOptional = Optional.of(combineOrder);
		when(combineOrderRepository.findById(checkoutPayment.getOrderId())).thenReturn(combineOrderOptional);
		User seller = new User();
		seller.setFullName("Test seller");
		seller.setEmail("testseller@gmail.com");
		seller.setMobileNumber("9999999999");
		seller.setPassword(bCryptPasswordEncoder.encode("12345678"));
		seller.setId(1l);
		Optional<User> optionalUser = Optional.of(seller);
		when(userRepository.findById(1l)).thenReturn(optionalUser);
		paymentServiceImpl.paymentForStripeProduct(checkoutPayment, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.PAYMENT_SESSION_NOT_CREATE));

	}
}
