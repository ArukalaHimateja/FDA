package com.fda.app.dto;

import java.util.List;

public class RestaurantRequestDto {

	private String restaurantName;
	private String restaurantAddress;
	private String restaurantLicenseNumber;
	private String ownerName;
	private String email;
	private String mobileNumber;
	private List<String> documents;

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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

	public String getRestaurantLicenseNumber() {
		return restaurantLicenseNumber;
	}

	public void setRestaurantLicenseNumber(String restaurantLicenseNumber) {
		this.restaurantLicenseNumber = restaurantLicenseNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public List<String> getDocuments() {
		return documents;
	}

	public void setDocuments(List<String> documents) {
		this.documents = documents;
	}

}
