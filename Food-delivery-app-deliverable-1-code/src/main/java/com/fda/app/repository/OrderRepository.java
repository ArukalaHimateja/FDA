package com.fda.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fda.app.constants.Constants;
import com.fda.app.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByStatus(int i);

	long countByStatus(int i);

	List<Order> findByCustomerId(long userId);

	boolean existsByIdAndCustomerId(Long orderId, Long customerId);

	long countByRestaurantId(Long id);

	Long countByRestaurantIdAndStatus(Long id, int i);

	long countDistinctCustomerIdByRestaurantId(Long id);

	List<Order> findByRestaurantId(long restaurantId);

	boolean existsByRestaurantId(long restaurantId);
	@Query(value = "SELECT SUM(e.total_pay_price) FROM "+Constants.ORDER_TABLE_NAME+" e where e.total_pay_price > 0", nativeQuery = true)
	Double countBytotalPayPrice();
	@Query(value = "SELECT SUM(e.total_pay_price) FROM "+Constants.ORDER_TABLE_NAME+" e where e.total_pay_price > 0 and e.restaurant_id =?1", nativeQuery = true)
	Double totalRestaurantRevenue(Long restaurantId);

	List<Order> findByOrderId(long orderId);

}
