package com.fda.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fda.app.model.TrackOrderHistory;

@Repository
public interface TrackOrderHistoryRepository extends JpaRepository<TrackOrderHistory, Long> {

	List<TrackOrderHistory> findByOrderId(long orderId);

}
