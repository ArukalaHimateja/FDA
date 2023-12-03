package com.fda.app.service.impl;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.BillingResponseDto;
import com.fda.app.model.Billing;
import com.fda.app.model.FoodProduct;
import com.fda.app.model.Order;
import com.fda.app.repository.BillingRepository;
import com.fda.app.repository.FoodProductRepository;
import com.fda.app.repository.OrderRepository;
import com.fda.app.service.IBillingService;

@Service
public class BillingServiceImpl implements IBillingService {
	@Autowired
	private FoodProductRepository productRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private BillingRepository billingRepository;

	@Override
	public void billingGenerate(@Valid long orderId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Order> order = orderRepository.findById(orderId);
		if (!order.isPresent()) {
			apiResponseDtoBuilder.withMessage("Order not found").withStatus(HttpStatus.OK);
			return;
		}
		Optional<FoodProduct> product = productRepository.findById(order.get().getProductId());
		if (!product.isPresent()) {
			apiResponseDtoBuilder.withMessage(Constants.PRODUCT_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
			return;
		}
		if (order.get().getPayStatus() == 1) {
			BillingResponseDto billingResponseDto = new BillingResponseDto();
			billingResponseDto.setproductName(product.get().getproductName());
			billingResponseDto.setOrderId(orderId);
			billingResponseDto.setPrice(product.get().getPrice());
			billingResponseDto.setTotalPayPrice(product.get().getPrice() * order.get().getProductQuantity());
			billingResponseDto.setproductQuantity(order.get().getProductQuantity());
			billingResponseDto.setCategoryId(product.get().getCategoryId());
			Billing billing = new Billing();
			billing.setCreatedAt(new Date());
			billing.setproductId(order.get().getProductId());
			billing.setOrderId(orderId);
			billing.setUserId(order.get().getCustomerId());
			billing.setTotalPayPrice(billingResponseDto.getTotalPayPrice());
			billing.setRestaurantId(order.get().getRestaurantId());
			billingRepository.save(billing);
			billingResponseDto.setBillingId(billing.getId());
			order.get().setStatus(2);
			orderRepository.save(order.get());
			apiResponseDtoBuilder.withMessage(Constants.BILLING_GENERATE_SUCCESSFULLY).withStatus(HttpStatus.OK)
					.withData(billingResponseDto);

		} else if (order.get().getPayStatus() == 0) {
			apiResponseDtoBuilder.withMessage(Constants.ORDER_PAY_PENDING).withStatus(HttpStatus.NOT_ACCEPTABLE);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.BILLING_GENERATE_ALREADY)
					.withStatus(HttpStatus.ALREADY_REPORTED);
		}

	}

	@Override
	public void getBillingById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Billing> billing = billingRepository.findById(id);
		if (billing.isPresent()) {
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(billing);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.FAIL).withStatus(HttpStatus.NOT_FOUND);
		}

	}

}
