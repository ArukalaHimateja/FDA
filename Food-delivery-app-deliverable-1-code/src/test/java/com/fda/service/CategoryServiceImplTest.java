package com.fda.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
import com.fda.app.service.impl.CategoryServiceImpl;
import com.fda.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
	@Mock
	CustomMapper customMapper;
	@InjectMocks
	private CategoryServiceImpl categoryServiceImpl;
	@Mock
	UserRepository userRepository;
	@Mock
	CategoryRepository categoryRepository;
	@Mock
	CategoryRepositoryCustom categoryRepositoryCustom;
	@Mock
	RestaurantRepository restaurantRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addCategory() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
		User user = new User();
		String email = "test123@gmail.com";
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		user.setRole(0);
		user.setId(1l);
		when(Utility.getSessionUser(userRepository)).thenReturn(user);
		Category category = new Category();
		category.setId(1l);
		when(customMapper.categoryRequestDtoToCategory(categoryRequestDto)).thenReturn(category);
		Restaurant restaurant=new Restaurant();
		restaurant.setId(1l);
		Optional<Restaurant> restaurantOptional=Optional.of(restaurant) ;
		when(restaurantRepository.findByUserId(1l)).thenReturn(restaurantOptional);
		categoryServiceImpl.addCategory(categoryRequestDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.ADD_CATEGORY_SUCCESS));

	}

	@Test
	public void getCategoryDetailsById() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Long id = 1l;
		Category category = new Category();
		category.setId(1l);
		Optional<Category> billingOptional = Optional.of(category);
		when(categoryRepository.findById(id)).thenReturn(billingOptional);
		categoryServiceImpl.getCategoryDetailsById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESS));

	}

	@Test
	public void getAllCategoryListByRestaurantId() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Long restaurantId = 1l;
		List<Category> billingOptional = new ArrayList<>();
		when(categoryRepository.findByRestaurantId(restaurantId)).thenReturn(billingOptional);
		categoryServiceImpl.getAllCategoryListByRestaurantId(restaurantId, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Category not found"));

	}

	@Test
	public void isActiveCategory() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Long id = 1l;
		Category category = new Category();
		category.setId(1l);
		Optional<Category> billingOptional = Optional.of(category);
		when(categoryRepository.findById(id)).thenReturn(billingOptional);
		categoryServiceImpl.isActiveCategory(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.CATEGORY_ACTIVE));

	}

	@Test
	public void isInactiveCategory() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Long id = 1l;
		Category category = new Category();
		category.setId(1l);
		Optional<Category> billingOptional = Optional.of(category);
		when(categoryRepository.findById(id)).thenReturn(billingOptional);
		categoryServiceImpl.isInactiveCategory(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.CATEGORY_INACTIVE));

	}

	@Test
	public void deleteCategoryById() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Long id = 1l;
		Category category = new Category();
		category.setId(1l);
		Optional<Category> billingOptional = Optional.of(category);
		when(categoryRepository.findById(id)).thenReturn(billingOptional);
		categoryServiceImpl.deleteCategoryById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.CATEGORY_DELETE));

	}

	@Test
	public void updateCategory() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		CategoryRequestDto category = new CategoryRequestDto();
		category.setDescription("test");
		categoryServiceImpl.updateCategory(category, 1l, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.CATEGORY_NOT_FOUND));

	}

	@Test
	public void getCategoryListByFilterWithPagination() {
		long id = 1;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		CategoryFilterWithPaginationDto categoryFilterWithPaginationDto = new CategoryFilterWithPaginationDto();
		PaginationDto pagination = new PaginationDto();
		when(categoryRepositoryCustom.getCategoryListByFilterWithPagination(categoryFilterWithPaginationDto))
				.thenReturn(pagination);
		categoryServiceImpl.getCategoryListByFilterWithPagination(categoryFilterWithPaginationDto,
				apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.DATA_LIST));
	}
}
