package com.fda.app.dto;

public class BillingResponseDto {

	private Long billingId;
	private Long orderId;
	private Long productQuantity;
	private String productName;
	private Double price;
	private Double totalPayPrice;
	private Long categoryId;
	private Long userId;

	public String getproductName() {
		return productName;
	}

	public void setproductName(String productName) {
		this.productName = productName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getBillingId() {
		return billingId;
	}

	public void setBillingId(Long billingId) {
		this.billingId = billingId;
	}

	public Long getproductQuantity() {
		return productQuantity;
	}

	public void setproductQuantity(Long productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotalPayPrice() {
		return totalPayPrice;
	}

	public void setTotalPayPrice(Double totalPayPrice) {
		this.totalPayPrice = totalPayPrice;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}
