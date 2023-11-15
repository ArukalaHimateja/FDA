package com.fda.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.UserFilterWithPaginationDto;
import com.fda.app.dto.UserRequestDto;
import com.fda.app.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class UserController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/user/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto addUser(@Valid @RequestBody UserRequestDto userRequestDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.addUser(userRequestDto, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/user/password/forgot", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto forgotPassword(@RequestParam(required = true) String email) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.sendTemporaryPassword(email, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@RequestMapping(value = "/get/user/all/details/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getUserAllDetailsById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.getUserAllDetailsById(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	
	@RequestMapping(value = "/user/list", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getAllUsersListDetails() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.getAllUserDetails(apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/user/active", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto isActiveUser(@RequestParam(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.isActiveUser(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@RequestMapping(value = "/user/inactive", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto isInactiveUser(@RequestParam(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.isInactiveUser(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/customer/pagination/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto getCustomerListByFilterWithPagination(
			@RequestBody UserFilterWithPaginationDto filterWithPagination) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.getCustomerListByFilterWithPagination(filterWithPagination, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
}
