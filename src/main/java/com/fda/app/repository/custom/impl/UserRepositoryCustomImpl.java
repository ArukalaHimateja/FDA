package com.fda.app.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fda.app.constants.Constants;
import com.fda.app.dto.PaginationDataDto;
import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.UserFilterWithPaginationDto;
import com.fda.app.dto.UserListResponseDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.User;
import com.fda.app.repository.custom.UserRepositoryCustom;
import com.fda.app.utility.Utility;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private CustomMapper customMapper;

	@Override
	public PaginationDto getCustomerListByFilterWithPagination(UserFilterWithPaginationDto filterWithPagination) {
		String countQuery = "SELECT count(*) from " + Constants.USER_TABLE_NAME + " t";
		String query = "SELECT t.* from " + Constants.USER_TABLE_NAME + " t";
		String addableQuery = " where t.role = 1";
		Query queryString = entityManager.createNativeQuery(countQuery + addableQuery);
		int totalCounts = ((Number) queryString.getSingleResult()).intValue();
		PaginationDataDto paginationDataDto = Utility.getPaginationData(totalCounts,
				filterWithPagination.getPagination());
		String limitQuery = " order by t.id desc limit " + paginationDataDto.getFrom() + ","
				+ paginationDataDto.getTo();
		queryString = entityManager.createNativeQuery(query + addableQuery + limitQuery, User.class);
		List<User> userList = queryString.getResultList();
		List<UserListResponseDto> dataList = customMapper.userListToUserListResponseDtoList(userList);
		filterWithPagination.getPagination().setData(dataList);
		filterWithPagination.getPagination().setTotalCount(totalCounts);
		filterWithPagination.getPagination().setTotalPages(paginationDataDto.getTotalPages());
		return filterWithPagination.getPagination();
	}
}
