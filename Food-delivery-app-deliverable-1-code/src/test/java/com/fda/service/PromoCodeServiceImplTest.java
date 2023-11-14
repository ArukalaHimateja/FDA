package com.fda.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;

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
import com.fda.app.dto.PromoCodeFilterWithPaginationDto;
import com.fda.app.dto.PromoCodeRequestDto;
import com.fda.app.mapper.CustomMapper;
import com.fda.app.model.PromoCode;
import com.fda.app.model.User;
import com.fda.app.repository.PromoCodeRepository;
import com.fda.app.repository.UserRepository;
import com.fda.app.repository.custom.PromoCodeRepositoyCustom;
import com.fda.app.service.IVerificationTokenService;
import com.fda.app.service.impl.PromoCodeServiceImpl;
import com.fda.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class PromoCodeServiceImplTest {

	@InjectMocks
	PromoCodeServiceImpl promoCodeServiceImpl;
	@Mock
	PromoCodeRepository promoCodeRepository;
	@Mock
	CustomMapper customMapper;
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Mock
	IVerificationTokenService verificationTokenService;
	@Mock
	UserRepository userRepository;
	@Mock
	private PromoCodeRepositoyCustom promoCodeRepositoryCustom;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	@Test
	public void addPromoCode() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		
		User user = new User();
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		user.setRole(0);
		when(Utility.getSessionUser(userRepository)).thenReturn(user);
		PromoCodeRequestDto promoCodeRequestDto = new PromoCodeRequestDto();
		promoCodeRequestDto.setDescription("123test");
		promoCodeRequestDto.setValue(3);
		promoCodeRequestDto.setPromoCode("@123test");
		PromoCode promoCode = new PromoCode();
		promoCode.setCreatedAt(new Date());
		when(customMapper.promoCodeRequestDtoToPromoCode(promoCodeRequestDto)).thenReturn(promoCode);
		promoCodeServiceImpl.addPromoCode(promoCodeRequestDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.PROMO_CODE_ADD_SUCCESS));

	}

	@Test
	public void getPromoCodeById() {
		long id = 1L;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		PromoCode promoCode = new PromoCode();
		promoCode.setCreatedAt(new Date());
		promoCodeServiceImpl.getPromoCodeById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.PROMO_CODE_NOT_FOUND));

	}

	@Test
	public void isPromocodeValid() {
		String promoCode = "test";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		PromoCode pCode = new PromoCode();
		pCode.setCreatedAt(new Date());
		pCode.setEndDate(new Date());
		pCode.setStartDate(new Date());
		when(promoCodeRepository.findByPromoCode(promoCode)).thenReturn(pCode);
		promoCodeServiceImpl.isPromocodeValid(promoCode, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.PROMO_CODE_EXPIRED));

	}

	@Test
	public void getPromoCodeByName() {
		String promoCode = "test";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();

		PromoCode code = new PromoCode();
		when(promoCodeRepository.findByPromoCode(promoCode)).thenReturn(code);
		promoCodeServiceImpl.getPromoCodeByName(promoCode, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Promo code is valid"));

	}

	@Test
	public void getPromoCodeListByFilterWithPagination() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		PromoCodeFilterWithPaginationDto filterWithPaginationDto = new PromoCodeFilterWithPaginationDto();
		filterWithPaginationDto.setPagination(null);
		PaginationDto paginationDto = new PaginationDto();
		when(promoCodeRepositoryCustom.getPromoCodesListByFilterWithPagination(filterWithPaginationDto))
				.thenReturn(paginationDto);
		promoCodeServiceImpl.getPromoCodesListByFilterWithPagination(filterWithPaginationDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.DATA_LIST));

	}

}
