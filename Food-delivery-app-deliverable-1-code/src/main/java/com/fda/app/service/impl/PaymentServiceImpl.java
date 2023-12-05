package com.fda.app.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.CheckoutPayment;
import com.fda.app.model.CombineOrder;
import com.fda.app.model.FoodProduct;
import com.fda.app.model.User;
import com.fda.app.repository.CombineOrderRepository;
import com.fda.app.repository.FoodProductRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.service.IPaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class PaymentServiceImpl implements IPaymentService {
	@Value("${stripe.api.key}")
	private String stripeApiKey;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FoodProductRepository foodProductRepository;

	@Autowired
	private CombineOrderRepository combineOrderRepository;

	@Override
	public void paymentForStripeProduct(CheckoutPayment checkoutPayment, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		try {
			Stripe.apiKey = stripeApiKey;
			Optional<CombineOrder> combineOrder = combineOrderRepository.findById(checkoutPayment.getOrderId());
			if (combineOrder.isPresent()) {
				Optional<User> user = userRepository.findById(combineOrder.get().getCustomerId());
				if (user.isPresent()) {
					Product product = null;
					Price price = null;
					try {
						Stripe.apiKey = stripeApiKey;
						ProductCreateParams paramsProduct = ProductCreateParams.builder().setActive(true)
								.setName(user.get().getFullName()).build();
						product = Product.create(paramsProduct);
						PriceCreateParams paramsa = PriceCreateParams.builder().setCurrency("usd")
								.setUnitAmount(
										(long) Double.parseDouble(combineOrder.get().getSubTotalPrice().toString()))
								.setProduct(product.getId()).build();

						price = Price.create(paramsa);
						combineOrder.get().setStripePriceId(price.getId());
						combineOrder.get().setProductId(product.getId());
						combineOrderRepository.save(combineOrder.get());
					} catch (StripeException e) {
						System.out.println(e.getMessage());
					}
					SessionCreateParams paramss = SessionCreateParams.builder()
							.setCustomer(user.get().getStripeUserId())
							.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
							.setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl(checkoutPayment.getSuccessUrl())
							.setCancelUrl(checkoutPayment.getCancelUrl()).addLineItem(SessionCreateParams.LineItem
									.builder().setPrice(combineOrder.get().getStripePriceId()).setQuantity(1l)
//									.setPriceData(SessionCreateParams.Lineproduct.PriceData.builder().setCurrency("GBP")
//											.setUnitAmount(subscriptionApply.get().getPrice())
//											.setProductData(SessionCreateParams.Lineproduct.PriceData.ProductData.builder()
//													.setName(checkoutPayment.getName()).build())
//											.build())
									.build())
							.build();
					Session session;

					session = Session.create(paramss);
					apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(session.getUrl());
				} else {
					apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
				}

			} else {
				apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
			}

		} catch (StripeException e) {
			apiResponseDtoBuilder.withMessage(Constants.PAYMENT_SESSION_NOT_CREATE).withStatus(HttpStatus.UNAUTHORIZED);
		}

	}

}
