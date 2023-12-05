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
import com.fda.app.dto.ProductFilterWithPaginationDto;
import com.fda.app.dto.ProductSearchFilterResponseDto;
import com.fda.app.dto.ProductSearchFilterWithPaginationDto;
import com.fda.app.model.FoodProduct;
import com.fda.app.repository.custom.ProductRepositoryCustom;
import com.fda.app.utility.Utility;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public PaginationDto getproductListByFilterWithPagination(ProductFilterWithPaginationDto filterWithPagination) {
		String countQuery = "SELECT count(*) from " + Constants.PRODUCT_TABLE_NAME + " t";
		String query = "SELECT t.* from " + Constants.PRODUCT_TABLE_NAME + " t";
		String addableQuery = "";
		boolean flag = false;
		boolean whereFlag = true;
		if (filterWithPagination.getFilter().getKeyword() != null
				&& !filterWithPagination.getFilter().getKeyword().isEmpty()) {
			addableQuery += Utility.addWhere(whereFlag) + " t.product_name like '%"
					+ filterWithPagination.getFilter().getKeyword() + "%'";
			flag = true;
			whereFlag = false;
		}
		if (filterWithPagination.getFilter().getRestaurantId() != 0) {
			addableQuery += Utility.addWhere(whereFlag) + Utility.addANDOrOR(flag) + " t.restaurant_id  like '%"
					+ filterWithPagination.getFilter().getRestaurantId() + "%'";
			flag = true;
			whereFlag = false;
		}
		if (filterWithPagination.getFilter().getCategoryId() != 0) {
			addableQuery += Utility.addWhere(whereFlag) + Utility.addANDOrOR(flag) + " t.category_id like '%"
					+ filterWithPagination.getFilter().getCategoryId() + "%'";
			flag = true;
			whereFlag = false;
		}

		Query queryString = entityManager.createNativeQuery(countQuery + addableQuery);
		int totalCounts = ((Number) queryString.getSingleResult()).intValue();
		PaginationDataDto paginationDataDto = Utility.getPaginationData(totalCounts,
				filterWithPagination.getPagination());
		String limitQuery = " order by t.id desc limit " + paginationDataDto.getFrom() + ","
				+ paginationDataDto.getTo();
		queryString = entityManager.createNativeQuery(query + addableQuery + limitQuery, FoodProduct.class);
		List<FoodProduct> productList = queryString.getResultList();
		filterWithPagination.getPagination().setData(productList);
		filterWithPagination.getPagination().setTotalCount(totalCounts);
		filterWithPagination.getPagination().setTotalPages(paginationDataDto.getTotalPages());
		return filterWithPagination.getPagination();
	}

	@Override
	public List<ProductSearchFilterResponseDto> getproductSearchListByFilterWithPagination(
			ProductSearchFilterWithPaginationDto productSearchFilterWithPaginationDto) {
		String query = "select r.restaurant_address,r.restaurant_name,p.product_name,p.id,p.price,p.category_name,p.product_image from "
				+ Constants.RESTAURANT_TABLE_NAME + " r join " + Constants.PRODUCT_TABLE_NAME
				+ " p on p.restaurant_id = r.id";

		String addableQuery = "";
		boolean flag = false;
		boolean whereFlag = true;
		if (productSearchFilterWithPaginationDto.getFilter().getKeyword() != null
				&& !productSearchFilterWithPaginationDto.getFilter().getKeyword().isEmpty()) {
			addableQuery += Utility.addWhere(whereFlag) + Utility.addANDOrOR(flag) + " p.product_name like '%"
					+ productSearchFilterWithPaginationDto.getFilter().getKeyword() + "%' or ";
			addableQuery += "r.restaurant_name like '%" + productSearchFilterWithPaginationDto.getFilter().getKeyword()
					+ "%'";
			flag = true;
			whereFlag = false;
		}
		if (productSearchFilterWithPaginationDto.getFilter().getLocation() != null
				&& !productSearchFilterWithPaginationDto.getFilter().getLocation().isEmpty()) {
			addableQuery += Utility.addWhere(whereFlag) + Utility.addANDOrOR(flag) + " r.restaurant_address like '%"
					+ productSearchFilterWithPaginationDto.getFilter().getLocation() + "%'";
			flag = true;
			whereFlag = false;
		}

		Query queryString = entityManager.createNativeQuery(query + addableQuery);
		List<Object[]> list = queryString.getResultList();
		List<ProductSearchFilterResponseDto> dataList = new ArrayList<>();
		for (Object[] objects : list) {
			ProductSearchFilterResponseDto productSearchFilterResponseDto = new ProductSearchFilterResponseDto();
			productSearchFilterResponseDto.setRestaurantAddress((String) objects[0]);
			productSearchFilterResponseDto.setRestaurantName((String) objects[1]);
			productSearchFilterResponseDto.setProductName((String) objects[2]);
			productSearchFilterResponseDto.setId(Long.parseLong(objects[3].toString()));
			productSearchFilterResponseDto.setPrice(Long.parseLong(objects[4].toString()));
			productSearchFilterResponseDto.setCategoryName((String) objects[5]);
			productSearchFilterResponseDto.setProductImage((String) objects[6]);
			dataList.add(productSearchFilterResponseDto);
		}
		return dataList;
	}

}
