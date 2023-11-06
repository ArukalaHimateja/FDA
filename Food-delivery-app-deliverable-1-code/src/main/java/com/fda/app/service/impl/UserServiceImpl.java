package com.fda.app.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fda.app.constants.Constants;
import com.fda.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fda.app.dto.PaginationDto;
import com.fda.app.dto.ProfileImageRequestDto;
import com.fda.app.dto.UserFilterWithPaginationDto;
import com.fda.app.dto.UserRequestDto;
import com.fda.app.dto.UserResponseDto;
import com.fda.app.dto.UserUpdateRequestDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.User;
import com.fda.app.repository.UserRepository;
import com.fda.app.repository.custom.UserRepositoryCustom;
import com.fda.app.service.IEmailService;
import com.fda.app.service.IUserService;
import com.fda.app.service.IVerificationTokenService;
import com.fda.app.utility.Utility;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;

@Service("userService")
public class UserServiceImpl implements IUserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Value("${stripe.api.key}")
	private String stripeApiKey;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CustomMapper customMapper;

	@Autowired
	private IVerificationTokenService verificationTokenService;

	@Autowired
	private IEmailService emailService;
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(Constants.INVALID_USERNAME_OR_PASSWORD);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public void addUser(UserRequestDto userRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request) {
		if (userRepository.existsByMobileNumber(userRequestDto.getMobileNumber())) {
			apiResponseDtoBuilder.withMessage(Constants.MOBILE_NUMBER_ALREADY_EXISTS)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if (userRepository.existsByEmail(userRequestDto.getEmail())) {
			apiResponseDtoBuilder.withMessage(Constants.EMAIL_ALREADY_EXISTS).withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		Stripe.apiKey = stripeApiKey;
		CustomerCreateParams params = CustomerCreateParams.builder().setEmail(userRequestDto.getEmail())
				.setName(userRequestDto.getFullName())
				.setShipping(CustomerCreateParams.Shipping.builder()
						.setAddress(CustomerCreateParams.Shipping.Address.builder()
								.setLine1(userRequestDto.getAddress()).build())
						.setName(userRequestDto.getFullName()).build())
				.setAddress(CustomerCreateParams.Address.builder().setLine1(userRequestDto.getAddress()).build())
				.build();
		try {
			Customer customer = Customer.create(params);

			User user = customMapper.userRequestDtoToUser(userRequestDto);
			user.setStripeUserId(customer.getId());
			String newPasswordEncodedString = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(newPasswordEncodedString);
			user.setCreatedAt(new Date());
			user.setRole(1);
			save(user);
			apiResponseDtoBuilder.withMessage(Constants.USER_ADD_SUCCESS).withStatus(HttpStatus.OK).withData(user);
			new Thread(() -> {
				verificationTokenService.sendVerificationToken(user);
			}).start();
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public User findByMobileNumber(String username) {
		return userRepository.findByMobileNumber(username);
	}

	@Override
	public User findByMobileNumberOrEmail(String mobileNumber, String email) {
		return userRepository.findByMobileNumberOrEmail(mobileNumber, email);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public User findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.isPresent() ? user.get() : null;
	}

	@Override
	@Transactional
	public void deleteUserById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User currentUser = Utility.getSessionUser(userRepository);
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			if (currentUser.getRole() == 0 || user.get().getId() == currentUser.getId()) {
				userRepository.deleteById(id);
				apiResponseDtoBuilder.withMessage(Constants.USER_DELETED_SUCCESSFULLY).withStatus(HttpStatus.OK);
			} else {
				apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
			}
		} else {
			apiResponseDtoBuilder.withMessage(Constants.USER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public User getSessionUser() {
		return Utility.getSessionUser(userRepository);
	}

	@Override
	public User findByMobileNumberOrEmailAndPassword(String username, String username2, String password) {
		return userRepository.findByMobileNumberOrEmailAndPassword(username, username2, password);
	}

	@Override
	public void sendTemporaryPassword(String email, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User user = findByEmail(email);
		if (user == null) {
			apiResponseDtoBuilder.withMessage(Constants.NO_EMAIL_EXISTS).withStatus(HttpStatus.NOT_FOUND);
			return;
		}
		String password = Utility.generateRandomPassword(8);
		String newPasswordEncodedString = bCryptPasswordEncoder.encode(password);
		user.setPassword(newPasswordEncodedString);
		save(user);
		apiResponseDtoBuilder.withMessage(Constants.SEND_DETAILS_TO_YOUR_EMAIL).withStatus(HttpStatus.OK);
		new Thread(() -> {
			String subject = "Temporary Password";
			String body = emailService.createEmailBodyForForgotPassword(user.getFullName(), password);
			emailService.sendEmail(user.getEmail(), subject, body, "", null, null);
		}).start();
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void changePassword(long userId, String oldPassword, String newPassword,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			try {
				if (bCryptPasswordEncoder.matches(oldPassword, user.get().getPassword())) {
					String newPasswordEncodedString = bCryptPasswordEncoder.encode(newPassword);
					user.get().setPassword(newPasswordEncodedString);
					userRepository.save(user.get());
					apiResponseDtoBuilder.withMessage(Constants.PASS_CHANGE_SUCCESSFULLY).withStatus(HttpStatus.OK);
				} else {
					apiResponseDtoBuilder.withMessage(Constants.OLD_PASSWORD_ID_WRONG)
							.withStatus(HttpStatus.BAD_REQUEST);
				}

			} catch (Exception e) {
				apiResponseDtoBuilder.withMessage("Exception in Password change : " + e.getMessage())
						.withStatus(HttpStatus.BAD_REQUEST);
			}
		} else {
			apiResponseDtoBuilder.withMessage(Constants.USER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void getAllUserDetails(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User user = Utility.getSessionUser(userRepository);
		if (user.getRole() == 0) {
			List<User> userList = userRepository.findAll();
			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(userList);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void isActiveUser(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			user.get().setActive(true);
			save(user.get());
			apiResponseDtoBuilder.withMessage(Constants.USER_ACTIVE_SUCCESS).withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.USER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void updateUser(@Valid UserUpdateRequestDto userUpdateRequestDto, long id,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User currentUser = Utility.getSessionUser(userRepository);
		if (currentUser.getRole() == 1 || currentUser.getRole() == 0) {
			Optional<User> userDb = userRepository.findById(id);
			if (userDb.isPresent()) {
				userDb.get().setFullName(userUpdateRequestDto.getFullName());
				userDb.get().setEmail(userUpdateRequestDto.getEmail());
				userDb.get().setMobileNumber(userUpdateRequestDto.getMobileNumber());
				userDb.get().setUpdatedAt(new Date());
				save(userDb.get());
				apiResponseDtoBuilder.withMessage(Constants.USER_UPDATED_SUCCESSFULLY).withStatus(HttpStatus.OK)
						.withData(userDb);
			} else {
				apiResponseDtoBuilder.withMessage(Constants.USER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
			}
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}

	}

	@Override
	public void getUserDetailsById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			UserResponseDto userResponseDto = new UserResponseDto();
			userResponseDto.setAddress(user.get().getAddress());
			userResponseDto.setEmail(user.get().getEmail());
			userResponseDto.setFullName(user.get().getFullName());
			userResponseDto.setMobileNumber(user.get().getMobileNumber());
			userResponseDto.setUserId(user.get().getId());
			userResponseDto.setRole(user.get().getRole());
			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(userResponseDto);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.USER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void getCustomerListByFilterWithPagination(UserFilterWithPaginationDto filterWithPagination,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		PaginationDto pagination = userRepositoryCustom.getCustomerListByFilterWithPagination(filterWithPagination);
		apiResponseDtoBuilder.withMessage(Constants.DATA_LIST).withStatus(HttpStatus.OK).withData(pagination);

	}

	@Override
	public void isInactiveUser(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			user.get().setActive(false);
			save(user.get());
			apiResponseDtoBuilder.withMessage(Constants.USER_DEACTIVE_SUCCESS).withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.USER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void getUserAllDetailsById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(user);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.USER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void addUserProfileImage(@Valid ProfileImageRequestDto profileImageRequestDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<User> userDb = userRepository.findById(profileImageRequestDto.getUserId());
		if (userDb.isPresent()) {
			userDb.get().setProfileImage(profileImageRequestDto.getProfileImageUrl());
			userRepository.save(userDb.get());

			apiResponseDtoBuilder.withMessage(Constants.IMAGE_ADD_SUCCESSFULLY).withStatus(HttpStatus.OK)
					.withData(userDb);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.USER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void updateUserAddress(@Valid String address, long userId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User currentUser = Utility.getSessionUser(userRepository);
		if (currentUser.getRole() == 1 || currentUser.getRole() == 0) {
			Optional<User> user = userRepository.findById(userId);
			if (user.isPresent()) {
				user.get().setAddress(address);
				user.get().setUpdatedAt(new Date());
				userRepository.save(user.get());

				apiResponseDtoBuilder.withMessage(Constants.USER_UPDATED_SUCCESSFULLY).withStatus(HttpStatus.OK)
						.withData(user.get());
			} else {
				apiResponseDtoBuilder.withMessage(Constants.USER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
			}
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

}