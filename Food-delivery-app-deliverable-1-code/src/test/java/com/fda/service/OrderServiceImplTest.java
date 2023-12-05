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
import com.fda.app.dto.OrderDto;
import com.fda.app.dto.OrderListDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.CombineOrder;
import com.fda.app.model.FoodProduct;
import com.fda.app.model.Order;
import com.fda.app.model.Restaurant;
import com.fda.app.model.User;
import com.fda.app.repository.CombineOrderRepository;
import com.fda.app.repository.FoodProductRepository;
import com.fda.app.repository.OrderRepository;
import com.fda.app.repository.RestaurantRepository;
import com.fda.app.repository.TrackOrderHistoryRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.service.IEmailService;
import com.fda.app.service.IVerificationTokenService;
import com.fda.app.service.impl.OrderServiceImpl;
import com.fda.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
	@Mock
	CustomMapper customMapper;

	@InjectMocks
	private OrderServiceImpl orderServiceImpl;

	@Mock
	private UserRepository userRepository;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private RestaurantRepository restaurantRepository;
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Mock
	private TrackOrderHistoryRepository trackOrderHistoryRepository;
	@Mock
	private IEmailService emailService;

	@Mock
	private IVerificationTokenService verificationTokenService;
	@Mock
	private FoodProductRepository productRepository;
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
	public void addOrder() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		OrderDto orderDto = new OrderDto();
		OrderListDto orderListDto = new OrderListDto();
		orderListDto.setProductId(1l);
		orderListDto.setProductQuantity(1l);
		List<OrderListDto> listOfOrder = new ArrayList<>();
		listOfOrder.add(orderListDto);
		orderDto.setOrderListDto(listOfOrder);

		User user = new User();
		String email = "test123@gmail.com";
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		user.setRole(0);
		when(Utility.getSessionUser(userRepository)).thenReturn(user);
		FoodProduct foodProduct = new FoodProduct();
		foodProduct.setId(1l);
		foodProduct.setRestaurantId(1l);
		foodProduct.setPrice(11.1);
		Optional<FoodProduct> optional = Optional.of(foodProduct);
		when(productRepository.findById(1l)).thenReturn(optional);
		Order order = new Order();

		order.setId(1l);
		when(customMapper.orderDtoToOrder(orderDto)).thenReturn(order);
		Restaurant restaurant = new Restaurant();
		Optional<Restaurant> optionalRestaurant = Optional.of(restaurant);
		when(restaurantRepository.findById(optional.get().getRestaurantId())).thenReturn(optionalRestaurant);
		orderServiceImpl.addOrder(orderDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.ORDER_ADD_SUCCESSFULLY));

	}

	@Test
	public void getAllOrderListDetails() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User user = new User();
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		user.setRole(0);
		when(Utility.getSessionUser(userRepository)).thenReturn(user);
		List<CombineOrder> orderList = new ArrayList<>();
		when(combineOrderRepository.findAll()).thenReturn(orderList);
		orderServiceImpl.getAllOrderListDetails(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESS));
	}

	@Test
	public void getOrderById() {
		long id = 3L;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		CombineOrder order = new CombineOrder();
		order.setId(1l);
		Optional<CombineOrder> optional = Optional.of(order);
		when(combineOrderRepository.findById(id)).thenReturn(optional);
		assertTrue(optional.isPresent());
		orderServiceImpl.getOrderById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESS));
	}

	@Test
	public void getAllOrderByUserId() {
		long id = 1L;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User seller = new User();
		seller.setFullName("Test seller");
		seller.setEmail("testseller@gmail.com");
		seller.setMobileNumber("9999999999");
		seller.setPassword(bCryptPasswordEncoder.encode("12345678"));
		seller.setId(id);
		Optional<User> optional = Optional.of(seller);
		when(userRepository.findById(id)).thenReturn(optional);
		List<Order> orderList = new ArrayList<>();
		Order order = new Order();
		order.setId(1l);
		orderList.add(order);
		when(orderRepository.findByCustomerId(1l)).thenReturn(orderList);
		orderServiceImpl.getAllOrderByUserId(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESS));
	}

}