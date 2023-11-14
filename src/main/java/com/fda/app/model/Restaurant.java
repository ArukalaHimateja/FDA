package com.fda.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fda.app.config.CustomDateAndTimeDeserialize;
import com.fda.app.config.CustomJsonDateSerializer;
import com.fda.app.constants.Constants;

@Entity
@Table(name = Constants.RESTAURANT_TABLE_NAME)
@JsonIgnoreProperties
public class Restaurant {

	public static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	@JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
	private Date createdAt;

	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	@JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
	private Date updatedAt;
	private String restaurantName;
	private String restaurantAddress;
	private String restaurantLicenseNumber;
	private String restaurantEmail;
	private String restaurantMobileNumber;
	private Long restaurantRequestId;
	private int status = 1;// 0-isInactive,1-isActive;
	private Long userId;

	public String getRestaurantMobileNumber() {
		return restaurantMobileNumber;
	}

	public void setRestaurantMobileNumber(String restaurantMobileNumber) {
		this.restaurantMobileNumber = restaurantMobileNumber;
	}

	public Long getRestaurantRequestId() {
		return restaurantRequestId;
	}

	public void setRestaurantRequestId(Long restaurantRequestId) {
		this.restaurantRequestId = restaurantRequestId;
	}

	public String getRestaurantLicenseNumber() {
		return restaurantLicenseNumber;
	}

	public void setRestaurantLicenseNumber(String restaurantLicenseNumber) {
		this.restaurantLicenseNumber = restaurantLicenseNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRestaurantEmail() {
		return restaurantEmail;
	}

	public void setRestaurantEmail(String restaurantEmail) {
		this.restaurantEmail = restaurantEmail;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
