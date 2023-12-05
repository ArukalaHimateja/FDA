package com.fda.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.CombineOrderFilterWithPaginationDto;
import com.fda.app.dto.OrderDto;
import com.fda.app.dto.OrderFilterWithPaginationDto;
import com.fda.app.service.IOrderService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class OrderController {
	@Autowired
	private IOrderService orderService;

	@RequestMapping(value = "/order/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto addOrder(@Valid @RequestBody OrderDto orderDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.addOrder(orderDto, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/get/all/order/list", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getAllproductListDetails() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.getAllOrderListDetails(apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/get/order/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getOrderById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.getOrderById(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@RequestMapping(value = "/get/sub/order/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getSubOrderById(@PathVariable(required = true) long orderId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.getSubOrderById(orderId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@RequestMapping(value = "/order/pending/status/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto orderPendingStatusByOrderId(@PathVariable(required = true) long orderId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.orderPendingStatusByOrderId(orderId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@RequestMapping(value = "/order/accepted/status/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto orderAcceptedStatusByOrderId(@PathVariable(required = true) long orderId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.orderAcceptedStatusByOrderId(orderId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/order/delivered/status/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto orderDeliverdStatusByOrderId(@PathVariable(required = true) long orderId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.orderDeliverdStatusByOrderId(orderId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@RequestMapping(value = "/order/cancel/status/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto orderCancelStatusByOrderId(@PathVariable(required = true) long orderId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.orderCancelStatusByOrderId(orderId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@RequestMapping(value = "/track/order/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto orderTrackByOrderId(@PathVariable(required = true) long orderId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.orderTrackByOrderId(orderId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@RequestMapping(value = "/get/all/order/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getAllOrderByUserId(@PathVariable(required = true) long userId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.getAllOrderByUserId(userId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@RequestMapping(value = "/get/all/order/restaurant/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getAllOrderByRestaurantId(@PathVariable(required = true) long restaurantId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.getAllOrderByRestaurantId(restaurantId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@RequestMapping(value = "/order/pagination/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto getOrderListByFilterWithPagination(
			@RequestBody OrderFilterWithPaginationDto orderFilterWithPaginationDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.getOrderListByFilterWithPagination(orderFilterWithPaginationDto, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@RequestMapping(value = "/combine/order/pagination/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto getCombineOrderListByFilterWithPagination(
			@RequestBody CombineOrderFilterWithPaginationDto orderFilterWithPaginationDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.getCombineOrderListByFilterWithPagination(orderFilterWithPaginationDto, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
//	@RequestMapping(value = "/order/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
//	public ApiResponseDto deleteOrderById(@PathVariable(required = true) long id) {
//		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
//		orderService.deleteOrderById(id, apiResponseDtoBuilder);
//		return apiResponseDtoBuilder.build();
//	}
	@RequestMapping(value = "/order/pay/status/confirmed/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto orderPayStatusConfirmedByOrderId(@PathVariable(required = true) long orderId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.orderPayStatusConfirmedByOrderId(orderId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
}
