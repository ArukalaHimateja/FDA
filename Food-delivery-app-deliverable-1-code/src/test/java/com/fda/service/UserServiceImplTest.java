package com.fda.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.ProfileImageRequestDto;
import com.fda.app.dto.UserFilterDto;
import com.fda.app.dto.UserFilterWithPaginationDto;
import com.fda.app.dto.UserRequestDto;
import com.fda.app.dto.UserResponseDto;
import com.fda.app.dto.UserUpdateRequestDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.User;
import com.fda.app.repository.UserRepository;
import com.fda.app.repository.custom.UserRepositoryCustom;
import com.fda.app.service.IEmailService;
import com.fda.app.service.IVerificationTokenService;
import com.fda.app.service.impl.UserServiceImpl;
import com.fda.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	@Mock
	CustomMapper customMapper;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Mock
	private UserRepository userRepository;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	private IEmailService emailService;

	@Mock
	private IVerificationTokenService verificationTokenService;
	@Mock
	private UserRepositoryCustom userRepositoryCustom;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addUser() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();

		UserRequestDto userDto = new UserRequestDto();
		userDto.setEmail("testadmin@gmail.com");
		userDto.setFullName("Test Admin");
		userDto.setMobileNumber("11111111");
		userDto.setPassword("12345");

		User user = new User();
		user.setId(1L);
		user.setRole(0);
		user.setActive(true);
		user.setEmail("testadmin@gmail.com");
		user.setFullName("Test Admin");
		user.setMobileNumber("11111111");
		user.setPassword("12345");
		user.setCreatedAt(new Date());
		when(customMapper.userRequestDtoToUser(userDto)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		userServiceImpl.addUser(userDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.USER_ADD_SUCCESS));

	}

	@Test
	public void forgotPassword() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		String email = "testadmin@gmail.com";
		userServiceImpl.sendTemporaryPassword(email, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("No email exists"));
	}

	@Test
	public void getUserAllDetailsById() {
		long id = 3L;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User seller = new User();
		seller.setFullName("Test seller");
		seller.setEmail("testseller@gmail.com");
		seller.setMobileNumber("9999999999");
		seller.setPassword(bCryptPasswordEncoder.encode("12345678"));
		seller.setId(id);
		Optional<User> optional = Optional.of(seller);
		when(userRepository.findById(id)).thenReturn(optional);
		assertTrue(optional.isPresent());
		userServiceImpl.getUserAllDetailsById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));
	}

	@Test
	public void getAllUserDetails() {
		long id = 3L;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User user = new User();
		String email = "test123@gmail.com";
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		user.setRole(0);
		when(Utility.getSessionUser(userRepository)).thenReturn(user);
		List<User> listOfUser = new ArrayList<>();
		listOfUser.add(user);
		when(userRepository.findAll()).thenReturn(listOfUser);
		userServiceImpl.getAllUserDetails(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));
	}

	@Test
	public void isActiveUser() {
		long id = 0;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User seller = new User();
		seller.setFullName("Test seller");
		seller.setEmail("testseller@gmail.com");
		seller.setMobileNumber("9999999999");
		seller.setPassword(bCryptPasswordEncoder.encode("12345678"));
		seller.setId(id);
		Optional<User> optional = Optional.of(seller);
		when(userRepository.findById(id)).thenReturn(optional);
		userServiceImpl.isActiveUser(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.USER_ACTIVE_SUCCESS));
	}

	@Test
	public void isInactiveUser() {
		long id = 1;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User seller = new User();
		seller.setFullName("Test seller");
		seller.setEmail("testseller@gmail.com");
		seller.setMobileNumber("9999999999");
		seller.setPassword(bCryptPasswordEncoder.encode("12345678"));
		seller.setId(id);
		Optional<User> optional = Optional.of(seller);
		when(userRepository.findById(id)).thenReturn(optional);
		userServiceImpl.isInactiveUser(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.USER_DEACTIVE_SUCCESS));
	}

	@Test
	public void getCustomerListByFilterWithPagination() {
		long id = 1;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		UserFilterWithPaginationDto userFilterWithPaginationDto = new UserFilterWithPaginationDto();
		UserFilterDto userFilterDto = new UserFilterDto();
		userFilterWithPaginationDto.setFilter(userFilterDto);
		PaginationDto pagination = new PaginationDto();
		when(userRepositoryCustom.getCustomerListByFilterWithPagination(userFilterWithPaginationDto))
				.thenReturn(pagination);
		userServiceImpl.getCustomerListByFilterWithPagination(userFilterWithPaginationDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.DATA_LIST));
	}

	@Test
	public void deleteUserById() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(0);
		Optional<User> customers = Optional.ofNullable(sessionUser);
		when(userRepository.findById(id)).thenReturn(customers);
		User user = new User();
		String email = "test123@gmail.com";
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		user.setRole(0);
		when(Utility.getSessionUser(userRepository)).thenReturn(user);
		userServiceImpl.deleteUserById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.USER_DELETED_SUCCESSFULLY));

	}

	@Test
	public void changePassword() {
		long userId = 1;
		String oldPassword = "string";
		String newPassword = "string";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		Optional<User> userValue = Optional.ofNullable(sessionUser);
		when(userRepository.findById(userId)).thenReturn(userValue);
		userServiceImpl.changePassword(userId, oldPassword, newPassword, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Old password is wrong"));

	}

	@Test
	public void updateUser() {
		Long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto();
		userUpdateRequestDto.setEmail("string");
		userUpdateRequestDto.setFullName("string");
		userUpdateRequestDto.setMobileNumber("string");
		User user = new User();
		String email = "test123@gmail.com";
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		user.setRole(0);
		when(Utility.getSessionUser(userRepository)).thenReturn(user);
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		Optional<User> userValue = Optional.ofNullable(sessionUser);
		when(userRepository.findById(1l)).thenReturn(userValue);
		userServiceImpl.updateUser(userUpdateRequestDto, id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.USER_UPDATED_SUCCESSFULLY));

	}

	@Test
	public void updateUserAddress() {
		Long id = 1l;
		String address = "test";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User user = new User();
		String email = "test123@gmail.com";
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		user.setRole(0);
		when(Utility.getSessionUser(userRepository)).thenReturn(user);
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		Optional<User> userValue = Optional.ofNullable(sessionUser);
		when(userRepository.findById(1l)).thenReturn(userValue);
		userServiceImpl.updateUserAddress(address, id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Profile updated successfully"));

	}

	@Test
	public void getUserDetailsById() {
		long id = 1l;
		User user = new User();
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		user.setAddress("test");
		user.setRole(0);
		user.setId(1l);
		UserResponseDto userResponseDto = new UserResponseDto();
		userResponseDto.setEmail("test");
		userResponseDto.setFullName("test");
		userResponseDto.setMobileNumber("123456789");
		userResponseDto.setAddress("test");
		userResponseDto.setRole(0);
		userResponseDto.setUserId(1l);
		Optional<User> employees = Optional.ofNullable(user);
		when(userRepository.findById(id)).thenReturn(employees);
		userServiceImpl.getUserDetailsById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}

	@Test
	public void addUserProfileImage() {
		long id = 1l;
		User user = new User();
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		ProfileImageRequestDto profileImageRequestDto = new ProfileImageRequestDto();
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		profileImageRequestDto.setUserId(1l);
		Optional<User> employees = Optional.ofNullable(user);
		when(userRepository.findById(id)).thenReturn(employees);
		userServiceImpl.addUserProfileImage(profileImageRequestDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.IMAGE_ADD_SUCCESSFULLY));

	}
}
