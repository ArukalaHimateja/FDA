package com.fda.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.CategoryFilterWithPaginationDto;
import com.fda.app.dto.CategoryRequestDto;
import com.fda.app.dto.PaginationDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.Category;
import com.fda.app.model.Restaurant;
import com.fda.app.model.User;
import com.fda.app.repository.CategoryRepository;
import com.fda.app.repository.RestaurantRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.repository.custom.CategoryRepositoryCustom;
import com.fda.app.service.ICategoryService;
import com.fda.app.utility.Utility;

@Service
public class CategoryServiceImpl implements ICategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CustomMapper customMapper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepositoryCustom categoryRepositoryCustom;
	@Autowired
	private RestaurantRepository restaurantRepository;

	@Override
	public void addCategory(@Valid CategoryRequestDto categoryRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User user = Utility.getSessionUser(userRepository);
		if (user.getRole() != 2 && user.getRole() != 0) {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
			return;
		}
		if (categoryRepository.existsByName(categoryRequestDto.getName())) {
			apiResponseDtoBuilder.withMessage(Constants.CATEGORY_ALREADY_CREATE)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		Category category = customMapper.categoryRequestDtoToCategory(categoryRequestDto);
		Optional<Restaurant> Restaurant = restaurantRepository.findByUserId(user.getId());
		if (Restaurant.isPresent()) {
			category.setRestaurantId(Restaurant.get().getId());
			category.setCreatedAt(new Date());
			categoryRepository.save(category);
			apiResponseDtoBuilder.withMessage(Constants.ADD_CATEGORY_SUCCESS).withStatus(HttpStatus.OK)
					.withData(category);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}

	}

	@Override
	public void getCategoryDetailsById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent()) {
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(category);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.CATEGORY_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void getAllCategoryListByRestaurantId(long restaurantId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Category> category = categoryRepository.findByRestaurantId(restaurantId);
		if (!category.isEmpty()) {
			apiResponseDtoBuilder.withMessage(Constants.SUCCESS).withStatus(HttpStatus.OK).withData(category);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.CATEGORY_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void isActiveCategory(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent()) {
			category.get().setStatus(true);
			categoryRepository.save(category.get());
			apiResponseDtoBuilder.withMessage(Constants.CATEGORY_ACTIVE).withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.CATEGORY_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void isInactiveCategory(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent()) {
			category.get().setStatus(false);
			categoryRepository.save(category.get());
			apiResponseDtoBuilder.withMessage(Constants.CATEGORY_INACTIVE).withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.CATEGORY_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void deleteCategoryById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent()) {
			categoryRepository.deleteById(id);
			apiResponseDtoBuilder.withMessage(Constants.CATEGORY_DELETE).withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.CATEGORY_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void getCategoryListByFilterWithPagination(CategoryFilterWithPaginationDto categoryFilterWithPaginationDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		PaginationDto pagination = categoryRepositoryCustom
				.getCategoryListByFilterWithPagination(categoryFilterWithPaginationDto);
		apiResponseDtoBuilder.withMessage(Constants.DATA_LIST).withStatus(HttpStatus.OK).withData(pagination);

	}

	@Override
	public void updateCategory(@Valid CategoryRequestDto categoryRequestDto, long id,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent()) {
			category.get().setName(categoryRequestDto.getName());
			category.get().setDescription(categoryRequestDto.getDescription());
			category.get().setImage(categoryRequestDto.getImage());
			category.get().setUpdatedAt(new Date());
			categoryRepository.save(category.get());
			apiResponseDtoBuilder.withMessage(Constants.UPDATE_CATEGORY_SUCCESS).withStatus(HttpStatus.OK)
					.withData(category.get());
		} else {
			apiResponseDtoBuilder.withMessage(Constants.CATEGORY_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}
	}

}
