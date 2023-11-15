package com.fda.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.fda.app.dto.FeedbackRequestDto;
import com.fda.app.dto.LoginResponseDto;
import com.fda.app.dto.UserListResponseDto;
import com.fda.app.dto.UserRequestDto;
import com.fda.app.model.User;

@Mapper(componentModel = "spring")
public interface CustomMapper {

	User userRequestDtoToUser(UserRequestDto userRequestDto);


	FoodProduct fdaDtoTofda(@Valid ProductDto fdaDto);

	Restaurant restaurantDtoToRestaurant(@Valid RestaurantDto restaurantDto);

	Feedback feedbackRequestDtoToFeedback(@Valid FeedbackRequestDto feedbackRequestDto);

	RatingAndReview ratingRequestDtoToRating(@Valid RatingAndReviewRequestDto ratingRequestDto);

	List<UserListResponseDto> userListToUserListResponseDtoList(List<User> userList);

	PromoCode promoCodeRequestDtoToPromoCode(@Valid PromoCodeRequestDto promoCodeRequestDto);

	Report reportDtoToReport(@Valid ReportDto reportDto);


	Category categoryRequestDtoToCategory(@Valid CategoryRequestDto categoryRequestDto);

	User restaurantOwnerRequestDtoToUser(RestaurantOwnerRequestDto restaurantOwnerRequestDto);

	RestaurantRequest restaurantRequestDtoToRestaurantRequest(@Valid RestaurantRequestDto restaurantRequestDto);

	LoginResponseDto userToLoginResponseDto(User checkUser);

}