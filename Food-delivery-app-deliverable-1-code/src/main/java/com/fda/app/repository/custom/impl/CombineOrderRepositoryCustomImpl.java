package com.fda.app.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.fda.app.constants.Constants;
import com.fda.app.dto.CombineOrderFilterWithPaginationDto;
import com.fda.app.dto.PaginationDataDto;
import com.fda.app.dto.PaginationDto;
import com.fda.app.model.CombineOrder;
import com.fda.app.repository.custom.CombineOrderRepositoryCustom;
import com.fda.app.utility.Utility;

@Repository
public class CombineOrderRepositoryCustomImpl implements CombineOrderRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public PaginationDto getCombineOrderListByFilterWithPagination(
			CombineOrderFilterWithPaginationDto orderFilterWithPaginationDto) {

		String countQuery = "SELECT count(*) from " + Constants.COMBINE_ORDER_TABLE_NAME + " t";
		String query = "SELECT t.* from " + Constants.COMBINE_ORDER_TABLE_NAME + " t";
		String addableQuery = "";

		boolean flag = false;
		boolean whereFlag = true;

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
		queryString = entityManager.createNativeQuery(query + addableQuery + limitQuery, CombineOrder.class);
		List<CombineOrder> orderList = queryString.getResultList();
		orderFilterWithPaginationDto.getPagination().setData(orderList);
		orderFilterWithPaginationDto.getPagination().setTotalCount(totalCounts);
		orderFilterWithPaginationDto.getPagination().setTotalPages(paginationDataDto.getTotalPages());
		return orderFilterWithPaginationDto.getPagination();

	}

}
