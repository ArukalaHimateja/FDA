package com.fda.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.CombineOrderFilterWithPaginationDto;
import com.fda.app.dto.OrderDto;
import com.fda.app.dto.OrderFilterWithPaginationDto;

@Service
public interface IOrderService {

	void addOrder(@Valid OrderDto orderDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllOrderListDetails(ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getOrderById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllOrderByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	//void deleteOrderById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void orderAcceptedStatusByOrderId(long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void orderCancelStatusByOrderId(long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void orderPendingStatusByOrderId(long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void orderTrackByOrderId(long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void orderDeliverdStatusByOrderId(long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void orderPayStatusConfirmedByOrderId(long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getOrderListByFilterWithPagination(OrderFilterWithPaginationDto orderFilterWithPaginationDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllOrderByRestaurantId(long restaurantId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getSubOrderById(long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getCombineOrderListByFilterWithPagination(CombineOrderFilterWithPaginationDto orderFilterWithPaginationDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder);

}
