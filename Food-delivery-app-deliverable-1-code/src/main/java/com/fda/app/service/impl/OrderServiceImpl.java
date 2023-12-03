package com.fda.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.CombineOrderFilterWithPaginationDto;
import com.fda.app.dto.OrderDto;
import com.fda.app.dto.OrderFilterWithPaginationDto;
import com.fda.app.dto.OrderListDto;
import com.fda.app.dto.PaginationDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.CombineOrder;
import com.fda.app.model.FoodProduct;
import com.fda.app.model.Order;
import com.fda.app.model.Restaurant;
import com.fda.app.model.TrackOrderHistory;
import com.fda.app.model.User;
import com.fda.app.repository.CombineOrderRepository;
import com.fda.app.repository.FoodProductRepository;
import com.fda.app.repository.OrderRepository;
import com.fda.app.repository.RestaurantRepository;
import com.fda.app.repository.TrackOrderHistoryRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.repository.custom.CombineOrderRepositoryCustom;
import com.fda.app.repository.custom.OrderRepositoryCustom;
import com.fda.app.service.IOrderService;
import com.fda.app.utility.Utility;

@Service
public class OrderServiceImpl implements IOrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomMapper customMapper;
	@Autowired
	private FoodProductRepository productRepository;
	@Autowired
	private TrackOrderHistoryRepository trackOrderHistoryRepository;
	@Autowired
	private OrderRepositoryCustom orderRepositoryCustom;
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private CombineOrderRepository combineOrderRepository;
	@Value("${stripe.api.key}")
	private String stripeApiKey;
	@Autowired
	private CombineOrderRepositoryCustom conbineOrderRepositoryCustom;

	@Override
	public void addOrder(@Valid OrderDto orderDto, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User user = Utility.getSessionUser(userRepository);
		if (user.getRole() == 0 || user.getRole() == 1) {
			CombineOrder combineOrder = new CombineOrder();
			combineOrder.setCreatedAt(new Date());
			combineOrder.setDeliveryAddress(user.getAddress());
			combineOrder.setCustomerId(user.getId());
			combineOrder.setPromoCode(orderDto.getPromoCode());
			combineOrder.setPayPriceWithoutPromoCode(orderDto.getPayPriceWithoutPromoCode());
			combineOrder.setPayPriceWithPromoCode(orderDto.getPayPriceWithPromoCode());
			combineOrder.setSubTotalPrice(orderDto.getSubTotalPrice());
			combineOrderRepository.save(combineOrder);

			List<OrderListDto> listOfOrderDto = orderDto.getOrderListDto();
			for (OrderListDto singleOrder : listOfOrderDto) {
				Optional<FoodProduct> product = productRepository.findById(singleOrder.getProductId());
				if (product.isPresent()) {
					Order order = customMapper.orderDtoToOrder(orderDto);
					order.setPerQuantityPrice(product.get().getPrice());
					order.setTotalPayPrice(singleOrder.getProductQuantity() * product.get().getPrice());
					order.setCustomerId(user.getId());
					order.setDeliveryAddress(user.getAddress());
					order.setRestaurantId(product.get().getRestaurantId());
					order.setProductName(product.get().getproductName());
					order.setCreatedAt(new Date());
					order.setOrderId(combineOrder.getId());
					order.setProductId(product.get().getId());
					order.setProductQuantity(singleOrder.getProductQuantity());

					Optional<Restaurant> restaurant = restaurantRepository.findById(product.get().getRestaurantId());
					if (restaurant.isPresent()) {
						order.setRestaurantName(restaurant.get().getRestaurantName());
					}
					orderRepository.save(order);
					TrackOrderHistory trackOrderHistory = new TrackOrderHistory();
					trackOrderHistory.setCreatedAt(new Date());
					trackOrderHistory.setOrderId(order.getId());
					trackOrderHistory.setStatus(order.getStatus());
					trackOrderHistoryRepository.save(trackOrderHistory);

					apiResponseDtoBuilder.withMessage(Constants.ORDER_ADD_SUCCESSFULLY).withStatus(HttpStatus.OK)
							.withData(combineOrder);
				} else {
					apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
				}
			}
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}

	}

	@Override
	public void getAllOrderListDetails(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User user = Utility.getSessionUser(userRepository);
		if (user.getRole() == 0) {
			List<CombineOrder> orderList = combineOrderRepository.findAll();
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(orderList);

		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void getOrderById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<CombineOrder> order = combineOrderRepository.findById(id);
		if (order.isPresent()) {
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(order);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void getAllOrderByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			List<Order> orderList = orderRepository.findByCustomerId(userId);
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(orderList);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}
	}

//	@Override
//	public void deleteOrderById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
//		Optional<Order> order = orderRepository.findById(id);
//		if (order.isPresent()) {
//			orderRepository.deleteById(id);
//			apiResponseDtoBuilder.withMessage(Constants.ORDER_DELETE_SUCCESSFULLY).withStatus(HttpStatus.OK)
//					.withData(order);
//		} else {
//			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
//		}
//
//	}

	@Override
	public void orderAcceptedStatusByOrderId(long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<CombineOrder> order = combineOrderRepository.findById(orderId);
		if (order.isPresent()) {
			order.get().setStatus(1);
			combineOrderRepository.save(order.get());

			TrackOrderHistory trackOrderHistory = new TrackOrderHistory();
			trackOrderHistory.setUpdatedAt(new Date());
			trackOrderHistory.setOrderId(order.get().getId());
			trackOrderHistory.setStatus(order.get().getStatus());
			trackOrderHistoryRepository.save(trackOrderHistory);

			apiResponseDtoBuilder.withMessage(Constants.ORDER_STATUS_CHANGE_SUCCESSFULLY).withStatus(HttpStatus.OK)
					.withData(order);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void orderCancelStatusByOrderId(long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<CombineOrder> order = combineOrderRepository.findById(orderId);
		if (order.isPresent()) {
			order.get().setStatus(2);
			combineOrderRepository.save(order.get());

			TrackOrderHistory trackOrderHistory = new TrackOrderHistory();
			trackOrderHistory.setUpdatedAt(new Date());
			trackOrderHistory.setOrderId(order.get().getId());
			trackOrderHistory.setStatus(order.get().getStatus());
			trackOrderHistoryRepository.save(trackOrderHistory);

			apiResponseDtoBuilder.withMessage(Constants.ORDER_STATUS_CHANGE_SUCCESSFULLY).withStatus(HttpStatus.OK)
					.withData(order);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void orderDeliverdStatusByOrderId(long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<CombineOrder> order = combineOrderRepository.findById(orderId);
		if (order.isPresent()) {
			order.get().setStatus(3);
			combineOrderRepository.save(order.get());

			TrackOrderHistory trackOrderHistory = new TrackOrderHistory();
			trackOrderHistory.setUpdatedAt(new Date());
			trackOrderHistory.setOrderId(order.get().getId());
			trackOrderHistory.setStatus(order.get().getStatus());
			trackOrderHistoryRepository.save(trackOrderHistory);

			apiResponseDtoBuilder.withMessage(Constants.ORDER_STATUS_CHANGE_SUCCESSFULLY).withStatus(HttpStatus.OK)
					.withData(order);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void orderPendingStatusByOrderId(long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder) {

		Optional<CombineOrder> order = combineOrderRepository.findById(orderId);
		if (order.isPresent()) {
			order.get().setStatus(0);
			combineOrderRepository.save(order.get());

			TrackOrderHistory trackOrderHistory = new TrackOrderHistory();
			trackOrderHistory.setUpdatedAt(new Date());
			trackOrderHistory.setOrderId(order.get().getId());
			trackOrderHistory.setStatus(order.get().getStatus());
			trackOrderHistoryRepository.save(trackOrderHistory);

			apiResponseDtoBuilder.withMessage(Constants.ORDER_STATUS_CHANGE_SUCCESSFULLY).withStatus(HttpStatus.OK)
					.withData(order);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void orderTrackByOrderId(long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<TrackOrderHistory> listOfTrackOrderHistory = trackOrderHistoryRepository.findByOrderId(orderId);
		if (listOfTrackOrderHistory != null) {

			apiResponseDtoBuilder.withMessage(Constants.ORDER_STATUS_CHANGE_SUCCESSFULLY).withStatus(HttpStatus.OK)
					.withData(listOfTrackOrderHistory);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void orderPayStatusConfirmedByOrderId(long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<CombineOrder> order = combineOrderRepository.findById(orderId);
		if (order.isPresent()) {
			order.get().setPayStatus(1);
			combineOrderRepository.save(order.get());
			List<Order> ordersList = orderRepository.findByOrderId(order.get().getId());
			for (Order order2 : ordersList) {
				order2.setPayStatus(1);
				orderRepository.save(order2);
			}
			apiResponseDtoBuilder.withMessage(Constants.ORDER_PAYMENT_SUCCESSFULLY).withStatus(HttpStatus.OK)
					.withData(order);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void getAllOrderByRestaurantId(long restaurantId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		if (orderRepository.existsByRestaurantId(restaurantId)) {
			List<Order> orderList = orderRepository.findByRestaurantId(restaurantId);
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(orderList);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void getOrderListByFilterWithPagination(OrderFilterWithPaginationDto orderFilterWithPaginationDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		PaginationDto pagination = orderRepositoryCustom
				.getOrderListByFilterWithPagination(orderFilterWithPaginationDto);
		apiResponseDtoBuilder.withMessage(Constants.DATA_LIST).withStatus(HttpStatus.OK).withData(pagination);

	}

	@Override
	public void getSubOrderById(long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Order> order = orderRepository.findByOrderId(orderId);
		if (order != null) {
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(order);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void getCombineOrderListByFilterWithPagination(CombineOrderFilterWithPaginationDto orderFilterWithPaginationDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		PaginationDto pagination = conbineOrderRepositoryCustom
				.getCombineOrderListByFilterWithPagination(orderFilterWithPaginationDto);
		apiResponseDtoBuilder.withMessage(Constants.DATA_LIST).withStatus(HttpStatus.OK).withData(pagination);

	}

}
