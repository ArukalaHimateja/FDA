package com.fda.app.dto;

public class RestaurantDto {

	private String restaurantName;
	private String restaurantAddress;
	private String restaurantEmail;
	private String restaurantLicenseNumber;
	private String restaurantMobileNumber;

	public String getRestaurantMobileNumber() {
		return restaurantMobileNumber;
	}

	public void setRestaurantMobileNumber(String restaurantMobileNumber) {
		this.restaurantMobileNumber = restaurantMobileNumber;
	}

	public String getRestaurantEmail() {
		return restaurantEmail;
	}

	public String getRestaurantLicenseNumber() {
		return restaurantLicenseNumber;
	}

	public void setRestaurantLicenseNumber(String restaurantLicenseNumber) {
		this.restaurantLicenseNumber = restaurantLicenseNumber;
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

}
