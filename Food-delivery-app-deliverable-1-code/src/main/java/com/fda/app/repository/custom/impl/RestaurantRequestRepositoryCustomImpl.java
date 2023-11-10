package com.fda.app.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.fda.app.constants.Constants;
import com.fda.app.dto.PaginationDataDto;
import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.RestaurantRequestFilterWithPaginationDto;
import com.fda.app.model.RestaurantRequest;
import com.fda.app.repository.custom.RestaurantRequestRepositoryCustom;
import com.fda.app.utility.Utility;

@Repository
public class RestaurantRequestRepositoryCustomImpl implements RestaurantRequestRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public PaginationDto getRestaurantRequestListByFilterWithPagination(
			RestaurantRequestFilterWithPaginationDto restaurantRequestFilterWithPaginationDto) {
		String countQuery = "SELECT count(*) from "+Constants.RESTAURANT_REQUEST_TABLE_NAME+" t";
		String query = "SELECT t.* from "+Constants.RESTAURANT_REQUEST_TABLE_NAME+" t";
		String addableQuery = " where t.status = "
				+ restaurantRequestFilterWithPaginationDto.getRestaurantRequestFilterDto().getStatus();
		Query queryString = entityManager.createNativeQuery(countQuery + addableQuery);
		int totalCounts = ((Number) queryString.getSingleResult()).intValue();
		PaginationDataDto paginationDataDto = Utility.getPaginationData(totalCounts,
				restaurantRequestFilterWithPaginationDto.getPagination());
		String limitQuery = " order by t.id desc limit " + paginationDataDto.getFrom() + ","
				+ paginationDataDto.getTo();
		queryString = entityManager.createNativeQuery(query + addableQuery + limitQuery, RestaurantRequest.class);
		List<RestaurantRequest> restaurantRequestList = queryString.getResultList();
		restaurantRequestFilterWithPaginationDto.getPagination().setData(restaurantRequestList);
		restaurantRequestFilterWithPaginationDto.getPagination().setTotalCount(totalCounts);
		restaurantRequestFilterWithPaginationDto.getPagination().setTotalPages(paginationDataDto.getTotalPages());
		return restaurantRequestFilterWithPaginationDto.getPagination();
	}

}
