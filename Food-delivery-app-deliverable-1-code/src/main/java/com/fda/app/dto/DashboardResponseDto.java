package com.fda.app.dto;

public class DashboardResponseDto {

	private Long totalUser;
	private Long totalRestaurant;
	private Long totalOrder;
	private Long totalEmployee;
	private Long totalOrderDelivered;
	private Long totalPendingOrder;
	private double totalRevenue;
	private double totalRestaurantRevenue;

	public double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public double getTotalRestaurantRevenue() {
		return totalRestaurantRevenue;
	}

	public void setTotalRestaurantRevenue(double totalRestaurantRevenue) {
		this.totalRestaurantRevenue = totalRestaurantRevenue;
	}

	public Long getTotalOrderDelivered() {
		return totalOrderDelivered;
	}

	public void setTotalOrderDelivered(Long totalOrderDelivered) {
		this.totalOrderDelivered = totalOrderDelivered;
	}

	public Long getTotalPendingOrder() {
		return totalPendingOrder;
	}

	public void setTotalPendingOrder(Long totalPendingOrder) {
		this.totalPendingOrder = totalPendingOrder;
	}

	public Long getTotalEmployee() {
		return totalEmployee;
	}

	public void setTotalEmployee(Long totalEmployee) {
		this.totalEmployee = totalEmployee;
	}

	public Long getTotalUser() {
		return totalUser;
	}

	public void setTotalUser(Long totalUser) {
		this.totalUser = totalUser;
	}

	public Long getTotalRestaurant() {
		return totalRestaurant;
	}

	public void setTotalRestaurant(Long totalRestaurant) {
		this.totalRestaurant = totalRestaurant;
	}

	public Long getTotalOrder() {
		return totalOrder;
	}

	public void setTotalOrder(Long totalOrder) {
		this.totalOrder = totalOrder;
	}

}
