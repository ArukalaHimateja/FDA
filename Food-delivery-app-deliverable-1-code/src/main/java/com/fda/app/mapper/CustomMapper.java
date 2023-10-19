package com.fda.app.mapper;

import java.util.List;

import javax.validation.Valid;

import org.mapstruct.Mapper;

import com.fda.app.dto.RestaurantDto;
import com.fda.app.dto.UserListResponseDto;
import com.fda.app.dto.UserRequestDto;
import com.fda.app.model.Restaurant;
import com.fda.app.model.User;

@Mapper(componentModel = "spring")
public interface CustomMapper {

	User userRequestDtoToUser(UserRequestDto userRequestDto);

	Restaurant restaurantDtoToRestaurant(@Valid RestaurantDto restaurantDto);

	List<UserListResponseDto> userListToUserListResponseDtoList(List<User> userList);

}