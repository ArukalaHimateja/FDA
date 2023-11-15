package com.fda.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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
import com.fda.app.dto.ProductDto;
import com.fda.app.dto.ProductUpdateDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.FoodProduct;
import com.fda.app.model.User;
import com.fda.app.repository.FoodProductRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.service.IEmailService;
import com.fda.app.service.IVerificationTokenService;
import com.fda.app.service.impl.ProductServiceImpl;
import com.fda.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
	@Mock
	CustomMapper customMapper;

	@InjectMocks
	private ProductServiceImpl productServiceImpl;

	@Mock
	private UserRepository userRepository;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	private IEmailService emailService;

	@Mock
	private IVerificationTokenService verificationTokenService;
	@Mock
	private FoodProductRepository foodProductRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addProduct() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();

		ProductDto productDto = new ProductDto();
		productDto.setCategoryId(1l);
		productDto.setProductName("test");
		User user = new User();
		String email = "test123@gmail.com";
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		user.setRole(0);
		when(Utility.getSessionUser(userRepository)).thenReturn(user);
		when(foodProductRepository.existsByproductName(productDto.getProductName())).thenReturn(true);
		productServiceImpl.addProduct(productDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.PRODUCT_NAME_ALREADY_REGISTERED));

	}

	@Test
	public void getAllProductListDetails() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		List<FoodProduct> restaurantList = new ArrayList<>();
		FoodProduct Restaurant = new FoodProduct();
		Restaurant.setId(1l);
		when(foodProductRepository.findAll()).thenReturn(restaurantList);
		productServiceImpl.getAllProductListDetails(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESS));
	}

	@Test
	public void isActiveProduct() {
		long id = 0;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		FoodProduct foodProduct = new FoodProduct();
		foodProduct.setId(1l);
		Optional<FoodProduct> optional = Optional.of(foodProduct);
		// Optional<FoodProduct> product = ;
		when(foodProductRepository.findById(id)).thenReturn(optional);
		productServiceImpl.isActiveProduct(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.PRODUCT_ACTIVE_SUCCESS));
	}

	@Test
	public void isInactiveProduct() {
		long id = 0;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		FoodProduct foodProduct = new FoodProduct();
		foodProduct.setId(1l);
		Optional<FoodProduct> optional = Optional.of(foodProduct);
		when(foodProductRepository.findById(id)).thenReturn(optional);
		productServiceImpl.isInactiveProduct(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.PRODUCT_DEACTIVE_SUCCESS));
	}

}