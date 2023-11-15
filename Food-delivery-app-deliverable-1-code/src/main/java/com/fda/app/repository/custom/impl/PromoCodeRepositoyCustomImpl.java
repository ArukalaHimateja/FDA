package com.fda.app.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.fda.app.constants.Constants;
import com.fda.app.dto.PaginationDataDto;
import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.PromoCodeFilterWithPaginationDto;
import com.fda.app.model.PromoCode;
import com.fda.app.repository.custom.PromoCodeRepositoyCustom;
import com.fda.app.utility.Utility;

@Repository
public class PromoCodeRepositoyCustomImpl implements PromoCodeRepositoyCustom {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public PaginationDto getPromoCodesListByFilterWithPagination(
			PromoCodeFilterWithPaginationDto filterWithPagination) {
		String countQuery = "SELECT count(*) from "+Constants.PROMO_CODE_TABLE_NAME+" t";
		String query = "SELECT t.* from "+Constants.PROMO_CODE_TABLE_NAME+" t";
		String addableQuery = "";
		Query queryString = entityManager.createNativeQuery(countQuery + addableQuery);
		int totalCounts = ((Number) queryString.getSingleResult()).intValue();
		PaginationDataDto paginationDataDto = Utility.getPaginationData(totalCounts,
				filterWithPagination.getPagination());
		String limitQuery = " order by t.id desc limit " + paginationDataDto.getFrom() + ","
				+ paginationDataDto.getTo();
		queryString = entityManager.createNativeQuery(query + addableQuery + limitQuery, PromoCode.class);
		List<PromoCode> promoCodeList = queryString.getResultList();
		filterWithPagination.getPagination().setData(promoCodeList);
		filterWithPagination.getPagination().setTotalCount(totalCounts);
		filterWithPagination.getPagination().setTotalPages(paginationDataDto.getTotalPages());
		return filterWithPagination.getPagination();
	}

}
