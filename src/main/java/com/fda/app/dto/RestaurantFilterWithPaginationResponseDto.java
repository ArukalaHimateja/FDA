package com.fda.app.dto;

public class RestaurantFilterWithPaginationResponseDto {
	private String restaurantName;
	private String restaurantAddress;
	private String restaurantLicenseNumber;
	private String restaurantEmail;
	private String restaurantMobileNumber;
	private int status;
	private Boolean verify;

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

	public String getRestaurantLicenseNumber() {
		return restaurantLicenseNumber;
	}

	public void setRestaurantLicenseNumber(String restaurantLicenseNumber) {
		this.restaurantLicenseNumber = restaurantLicenseNumber;
	}

	public String getRestaurantEmail() {
		return restaurantEmail;
	}

	public void setRestaurantEmail(String restaurantEmail) {
		this.restaurantEmail = restaurantEmail;
	}

	public String getRestaurantMobileNumber() {
		return restaurantMobileNumber;
	}

	public void setRestaurantMobileNumber(String restaurantMobileNumber) {
		this.restaurantMobileNumber = restaurantMobileNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Boolean getVerify() {
		return verify;
	}

	public void setVerify(Boolean verify) {
		this.verify = verify;
	}

}
