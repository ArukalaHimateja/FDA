package com.fda.app.dto;

public class RestaurantDto {

	private String restaurantName;
	private String restaurantAddress;
	private String restaurantGstNo;
	private String ownerAadharNumber;
	private String restaurantEmail;

	public String getRestaurantEmail() {
		return restaurantEmail;
	}

	public void setRestaurantEmail(String restaurantEmail) {
		this.restaurantEmail = restaurantEmail;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getRestaurantAddress() {
		return restaurantAddress;
	}

	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}

	public String getRestaurantGstNo() {
		return restaurantGstNo;
	}

	public void setRestaurantGstNo(String restaurantGstNo) {
		this.restaurantGstNo = restaurantGstNo;
	}

	public String getOwnerAadharNumber() {
		return ownerAadharNumber;
	}

	public void setOwnerAadharNumber(String ownerAadharNumber) {
		this.ownerAadharNumber = ownerAadharNumber;
	}

}
