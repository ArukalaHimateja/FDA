package com.fda.app.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.fda.app.constants.Constants;
import com.fda.app.dto.OrderFilterWithPaginationDto;
import com.fda.app.dto.PaginationDataDto;
import com.fda.app.dto.PaginationDto;
import com.fda.app.model.Order;
import com.fda.app.repository.custom.OrderRepositoryCustom;
import com.fda.app.utility.Utility;

@Repository
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public PaginationDto getOrderListByFilterWithPagination(OrderFilterWithPaginationDto orderFilterWithPaginationDto) {

		String countQuery = "SELECT count(*) from " + Constants.ORDER_TABLE_NAME + " t";
		String query = "SELECT t.* from " + Constants.ORDER_TABLE_NAME + " t";
		String addableQuery = "";

		boolean flag = false;
		boolean whereFlag = true;

		if (orderFilterWithPaginationDto.getFilter().getRestaurantId() != 0) {
			addableQuery += Utility.addWhere(whereFlag) + Utility.addANDOrOR(flag) + " t.restaurant_id = "
					+ orderFilterWithPaginationDto.getFilter().getRestaurantId();
			flag = true;
			whereFlag = false;
		}

		if (orderFilterWithPaginationDto.getFilter().getStatus() != -1) {
			addableQuery += Utility.addWhere(whereFlag) + Utility.addANDOrOR(flag) + " t.status = "
					+ orderFilterWithPaginationDto.getFilter().getStatus();
			flag = true;
			whereFlag = false;
		}

		Query queryString = entityManager.createNativeQuery(countQuery + addableQuery);
		int totalCounts = ((Number) queryString.getSingleResult()).intValue();
		PaginationDataDto paginationDataDto = Utility.getPaginationData(totalCounts,
				orderFilterWithPaginationDto.getPagination());
		String limitQuery = " order by t.id desc limit " + paginationDataDto.getFrom() + ","
				+ paginationDataDto.getTo();
		queryString = entityManager.createNativeQuery(query + addableQuery + limitQuery, Order.class);
		List<Order> orderList = queryString.getResultList();
		orderFilterWithPaginationDto.getPagination().setData(orderList);
		orderFilterWithPaginationDto.getPagination().setTotalCount(totalCounts);
		orderFilterWithPaginationDto.getPagination().setTotalPages(paginationDataDto.getTotalPages());
		return orderFilterWithPaginationDto.getPagination();

	}

}
