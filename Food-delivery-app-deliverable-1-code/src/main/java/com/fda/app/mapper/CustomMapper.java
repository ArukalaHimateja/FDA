package com.fda.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.fda.app.dto.LoginResponseDto;
import com.fda.app.dto.UserListResponseDto;
import com.fda.app.dto.UserRequestDto;
import com.fda.app.model.User;

@Mapper(componentModel = "spring")
public interface CustomMapper {

	User userRequestDtoToUser(UserRequestDto userRequestDto);

	List<UserListResponseDto> userListToUserListResponseDtoList(List<User> userList);

	LoginResponseDto userToLoginResponseDto(User checkUser);

}