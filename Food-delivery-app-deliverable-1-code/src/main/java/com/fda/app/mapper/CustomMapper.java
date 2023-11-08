package com.fda.app.mapper;

import java.util.List;

import javax.validation.Valid;

import org.mapstruct.Mapper;

import com.fda.app.dto.CategoryRequestDto;
import com.fda.app.dto.LoginResponseDto;
import com.fda.app.dto.ProductDto;
import com.fda.app.dto.RatingRequestDto;
import com.fda.app.dto.RestaurantDto;
import com.fda.app.dto.RestaurantOwnerRequestDto;
import com.fda.app.dto.RestaurantRequestDto;
import com.fda.app.dto.ReviewDto;
import com.fda.app.dto.UserListResponseDto;
import com.fda.app.dto.UserRequestDto;
import com.fda.app.model.Category;
import com.fda.app.model.FoodProduct;
import com.fda.app.model.Rating;
import com.fda.app.model.Restaurant;
import com.fda.app.model.RestaurantRequest;
import com.fda.app.model.Review;
import com.fda.app.model.User;

@Mapper(componentModel = "spring")
public interface CustomMapper {

	User userRequestDtoToUser(UserRequestDto userRequestDto);

	FoodProduct fdaDtoTofda(@Valid ProductDto fdaDto);

	Restaurant restaurantDtoToRestaurant(@Valid RestaurantDto restaurantDto);

	Rating ratingRequestDtoToRating(@Valid RatingRequestDto ratingRequestDto);

	List<UserListResponseDto> userListToUserListResponseDtoList(List<User> userList);

	Review orderReviewDtoToOrderReview(ReviewDto orderReviewDto);

	Category categoryRequestDtoToCategory(@Valid CategoryRequestDto categoryRequestDto);

	User restaurantOwnerRequestDtoToUser(RestaurantOwnerRequestDto restaurantOwnerRequestDto);

	RestaurantRequest restaurantRequestDtoToRestaurantRequest(@Valid RestaurantRequestDto restaurantRequestDto);

	LoginResponseDto userToLoginResponseDto(User checkUser);

}