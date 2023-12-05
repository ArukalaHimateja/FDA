package com.fda.app.dto;

import java.util.List;

public class OrderDto {

	
	private String promoCode;
	private Double payPriceWithoutPromoCode;
	private Double payPriceWithPromoCode;
	private List<OrderListDto> orderListDto;
	private Double subTotalPrice;
	public Double getSubTotalPrice() {
		return subTotalPrice;
	}

	public void setSubTotalPrice(Double subTotalPrice) {
		this.subTotalPrice = subTotalPrice;
	}

	public Double getPayPriceWithoutPromoCode() {
		return payPriceWithoutPromoCode;
	}

	public void setPayPriceWithoutPromoCode(Double payPriceWithoutPromoCode) {
		this.payPriceWithoutPromoCode = payPriceWithoutPromoCode;
	}

	public Double getPayPriceWithPromoCode() {
		return payPriceWithPromoCode;
	}

	public void setPayPriceWithPromoCode(Double payPriceWithPromoCode) {
		this.payPriceWithPromoCode = payPriceWithPromoCode;
	}

	public List<OrderListDto> getOrderListDto() {
		return orderListDto;
	}

	public void setOrderListDto(List<OrderListDto> orderListDto) {
		this.orderListDto = orderListDto;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

}
