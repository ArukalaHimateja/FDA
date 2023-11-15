package com.fda.app.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.fda.app.constants.Constants;
import com.fda.app.dto.CategoryFilterWithPaginationDto;
import com.fda.app.dto.PaginationDataDto;
import com.fda.app.dto.PaginationDto;
import com.fda.app.model.Category;
import com.fda.app.model.Feedback;
import com.fda.app.repository.custom.CategoryRepositoryCustom;
import com.fda.app.utility.Utility;

@Repository
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public PaginationDto getCategoryListByFilterWithPagination(
			CategoryFilterWithPaginationDto categoryFilterWithPaginationDto) {
		String countQuery = "SELECT count(*) from "+Constants.CATEGORY_TABLE_NAME+" t";
		String query = "SELECT t.* from "+Constants.CATEGORY_TABLE_NAME+" t";
		String addableQuery = "";
		Query queryString = entityManager.createNativeQuery(countQuery + addableQuery);
		int totalCounts = ((Number) queryString.getSingleResult()).intValue();
		PaginationDataDto paginationDataDto = Utility.getPaginationData(totalCounts,
				categoryFilterWithPaginationDto.getPagination());
		String limitQuery = " order by t.id desc limit " + paginationDataDto.getFrom() + ","
				+ paginationDataDto.getTo();
		queryString = entityManager.createNativeQuery(query + addableQuery + limitQuery, Category.class);
		List<Category> categoryList = queryString.getResultList();
		categoryFilterWithPaginationDto.getPagination().setData(categoryList);
		categoryFilterWithPaginationDto.getPagination().setTotalCount(totalCounts);
		categoryFilterWithPaginationDto.getPagination().setTotalPages(paginationDataDto.getTotalPages());
		return categoryFilterWithPaginationDto.getPagination();
	}

}
