package com.fda.app.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.fda.app.constants.Constants;
import com.fda.app.dto.PaginationDataDto;
import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.ReportFilterWithPaginationDto;
import com.fda.app.model.Report;
import com.fda.app.repository.custom.ReportRepositoryCustom;
import com.fda.app.utility.Utility;

@Repository
public class ReportRepositoryCustomImpl implements ReportRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public PaginationDto getReportListByFilterWithPagination(ReportFilterWithPaginationDto filterWithPagination) {

		String countQuery = "SELECT count(*) from " + Constants.REPORT_TABLE_NAME + " t";
		String query = "SELECT t.* from " + Constants.REPORT_TABLE_NAME + " t";
		String addableQuery = "";
		Query queryString = entityManager.createNativeQuery(countQuery + addableQuery);
		int totalCounts = ((Number) queryString.getSingleResult()).intValue();
		PaginationDataDto paginationDataDto = Utility.getPaginationData(totalCounts,
				filterWithPagination.getPagination());
		String limitQuery = " order by t.id desc limit " + paginationDataDto.getFrom() + ","
				+ paginationDataDto.getTo();
		queryString = entityManager.createNativeQuery(query + addableQuery + limitQuery, Report.class);
		List<Report> promoCodeList = queryString.getResultList();
		filterWithPagination.getPagination().setData(promoCodeList);
		filterWithPagination.getPagination().setTotalCount(totalCounts);
		filterWithPagination.getPagination().setTotalPages(paginationDataDto.getTotalPages());
		return filterWithPagination.getPagination();

	}

}
