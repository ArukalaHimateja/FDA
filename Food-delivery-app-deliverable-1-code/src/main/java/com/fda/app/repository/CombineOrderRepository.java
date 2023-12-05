package com.fda.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fda.app.model.CombineOrder;

@Repository
public interface CombineOrderRepository extends JpaRepository<CombineOrder, Long> {

	//List<CombineOrder> findByOrderId(long orderId);

}
