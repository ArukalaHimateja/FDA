package com.fda.app.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fda.app.constants.Constants;
import com.fda.app.dto.FeedbackFilterWithPaginationDto;
import com.fda.app.dto.PaginationDataDto;
import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.UserListResponseDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.Feedback;
import com.fda.app.model.User;
import com.fda.app.repository.custom.FeedbackRepositoryCustom;
import com.fda.app.utility.Utility;

@Repository
public class FeedbackRepositoryCustomImpl implements FeedbackRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public PaginationDto getFeedbackListByFilterWithPagination(FeedbackFilterWithPaginationDto filterWithPagination) {
		String countQuery = "SELECT count(*) from "+Constants.FEEDBACK_TABLE_NAME+" t";
		String query = "SELECT t.* from "+Constants.FEEDBACK_TABLE_NAME+" t";
		String addableQuery = "";
		Query queryString = entityManager.createNativeQuery(countQuery + addableQuery);
		int totalCounts = ((Number) queryString.getSingleResult()).intValue();
		PaginationDataDto paginationDataDto = Utility.getPaginationData(totalCounts,
				filterWithPagination.getPagination());
		String limitQuery = " order by t.id desc limit " + paginationDataDto.getFrom() + ","
				+ paginationDataDto.getTo();
		queryString = entityManager.createNativeQuery(query + addableQuery + limitQuery, Feedback.class);
		List<Feedback> feedbackList = queryString.getResultList();
		filterWithPagination.getPagination().setData(feedbackList);
		filterWithPagination.getPagination().setTotalCount(totalCounts);
		filterWithPagination.getPagination().setTotalPages(paginationDataDto.getTotalPages());
		return filterWithPagination.getPagination();
	}

}
