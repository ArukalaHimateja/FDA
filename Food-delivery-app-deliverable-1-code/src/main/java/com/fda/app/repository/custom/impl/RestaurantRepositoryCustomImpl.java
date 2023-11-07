package com.fda.app.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.fda.app.constants.Constants;
import com.fda.app.dto.PaginationDataDto;
import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.RestaurantFilterWithPaginationDto;
import com.fda.app.dto.RestaurantFilterWithPaginationResponseDto;
import com.fda.app.dto.RestaurantRequestFilterWithPaginationDto;
import com.fda.app.model.Restaurant;
import com.fda.app.repository.custom.RestaurantRepositoryCustom;
import com.fda.app.utility.Utility;

@Repository
public class RestaurantRepositoryCustomImpl implements RestaurantRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Restaurant> getRestaurantListByRestaurantName(String keyword) {
		String query = "SELECT t.* from " + Constants.RESTAURANT_TABLE_NAME + " t";
		String addableQuery = " where t.restaurant_name like '%" + keyword + "%' or" + " t.restaurant_address like '%"
				+ keyword + "%'";
		Query queryString = entityManager.createNativeQuery(query + addableQuery, Restaurant.class);
		List<Restaurant> customerList = queryString.getResultList();
		return customerList;
	}

	@Override
	public PaginationDto getRestaurantListByFilterWithPagination(
			RestaurantFilterWithPaginationDto filterWithPagination) {
		String query = "select u.verify,p.restaurant_name,p.restaurant_address,p.restaurant_license_number,p.restaurant_email,p.restaurant_mobile_number,p.status from "
				+ Constants.USER_TABLE_NAME + " u join " + Constants.RESTAURANT_TABLE_NAME + " p on p.user_id = u.id";

		boolean andFlag = true;
		boolean whereFlag = false;

		Query queryString = entityManager.createNativeQuery(query);
		int totalCounts = queryString.getResultList().size();
		PaginationDataDto paginationDataDto = Utility.getPaginationData(totalCounts,
				filterWithPagination.getPagination());
		queryString = entityManager.createNativeQuery(query).setFirstResult(paginationDataDto.getFrom())
				.setMaxResults(paginationDataDto.getTo());
		List<Object[]> list = queryString.getResultList();
		List<RestaurantFilterWithPaginationResponseDto> dataList = new ArrayList<>();
		for (Object[] objects : list) {
			RestaurantFilterWithPaginationResponseDto restaurantFilterWithPaginationResponseDto = new RestaurantFilterWithPaginationResponseDto();
			restaurantFilterWithPaginationResponseDto.setVerify((Boolean) objects[0]);
			restaurantFilterWithPaginationResponseDto.setRestaurantName((String) objects[1]);
			restaurantFilterWithPaginationResponseDto.setRestaurantAddress((String) objects[2]);
			restaurantFilterWithPaginationResponseDto.setRestaurantLicenseNumber((String) objects[3]);
			restaurantFilterWithPaginationResponseDto.setRestaurantEmail((String) objects[4]);
			restaurantFilterWithPaginationResponseDto.setRestaurantMobileNumber((String) objects[5]);
			restaurantFilterWithPaginationResponseDto.setStatus((Integer) objects[6]);
			dataList.add(restaurantFilterWithPaginationResponseDto);
		}
		filterWithPagination.getPagination().setTotalCount(totalCounts);
		filterWithPagination.getPagination().setTotalPages(paginationDataDto.getTotalPages());
		filterWithPagination.getPagination().setData(dataList);
		return filterWithPagination.getPagination();
	}

	@Override
	public PaginationDto getRestaurantListByFilterWithPagination(
			RestaurantRequestFilterWithPaginationDto restaurantFilterWithPaginationDto) {
		String countQuery = "SELECT count(*) from " + Constants.RESTAURANT_TABLE_NAME + " t";
		String query = "SELECT t.* from " + Constants.RESTAURANT_TABLE_NAME + " t";
		String addableQuery = " where t.status = "
				+ restaurantFilterWithPaginationDto.getRestaurantRequestFilterDto().getStatus();
		Query queryString = entityManager.createNativeQuery(countQuery + addableQuery);
		int totalCounts = ((Number) queryString.getSingleResult()).intValue();
		PaginationDataDto paginationDataDto = Utility.getPaginationData(totalCounts,
				restaurantFilterWithPaginationDto.getPagination());
		String limitQuery = " order by t.id desc limit " + paginationDataDto.getFrom() + ","
				+ paginationDataDto.getTo();
		queryString = entityManager.createNativeQuery(query + addableQuery + limitQuery, Restaurant.class);
		List<Restaurant> restaurantList = queryString.getResultList();
		restaurantFilterWithPaginationDto.getPagination().setData(restaurantList);
		restaurantFilterWithPaginationDto.getPagination().setTotalCount(totalCounts);
		restaurantFilterWithPaginationDto.getPagination().setTotalPages(paginationDataDto.getTotalPages());
		return restaurantFilterWithPaginationDto.getPagination();
	}

}
