package com.fda.app.mapper;

import java.util.List;

import javax.validation.Valid;

import org.mapstruct.Mapper;

<<<<<<< HEAD
=======
import com.fda.app.dto.CategoryRequestDto;
import com.fda.app.dto.FeedbackRequestDto;
>>>>>>> d3b79d566c932f262577726729be4822ab33c101
import com.fda.app.dto.LoginResponseDto;
import com.fda.app.dto.ProductDto;
import com.fda.app.dto.PromoCodeRequestDto;
import com.fda.app.dto.RatingAndReviewRequestDto;
import com.fda.app.dto.ReportDto;
import com.fda.app.dto.RestaurantDto;
import com.fda.app.dto.RestaurantOwnerRequestDto;
import com.fda.app.dto.RestaurantRequestDto;
import com.fda.app.dto.UserListResponseDto;
import com.fda.app.dto.UserRequestDto;
import com.fda.app.model.Category;
import com.fda.app.model.Feedback;
import com.fda.app.model.FoodProduct;
import com.fda.app.model.PromoCode;
import com.fda.app.model.RatingAndReview;
import com.fda.app.model.Report;
import com.fda.app.model.Restaurant;
import com.fda.app.model.RestaurantRequest;
import com.fda.app.model.User;

@Mapper(componentModel = "spring")
public interface CustomMapper {

	User userRequestDtoToUser(UserRequestDto userRequestDto);

<<<<<<< HEAD
	List<UserListResponseDto> userListToUserListResponseDtoList(List<User> userList);

=======
	FoodProduct fdaDtoTofda(@Valid ProductDto fdaDto);

	Restaurant restaurantDtoToRestaurant(@Valid RestaurantDto restaurantDto);

	Feedback feedbackRequestDtoToFeedback(@Valid FeedbackRequestDto feedbackRequestDto);

	RatingAndReview ratingRequestDtoToRating(@Valid RatingAndReviewRequestDto ratingRequestDto);

	List<UserListResponseDto> userListToUserListResponseDtoList(List<User> userList);

	Category categoryRequestDtoToCategory(@Valid CategoryRequestDto categoryRequestDto);

	User restaurantOwnerRequestDtoToUser(RestaurantOwnerRequestDto restaurantOwnerRequestDto);

	RestaurantRequest restaurantRequestDtoToRestaurantRequest(@Valid RestaurantRequestDto restaurantRequestDto);

	PromoCode promoCodeRequestDtoToPromoCode(@Valid PromoCodeRequestDto promoCodeRequestDto);

	Report reportDtoToReport(@Valid ReportDto reportDto);

>>>>>>> d3b79d566c932f262577726729be4822ab33c101
	LoginResponseDto userToLoginResponseDto(User checkUser);

}