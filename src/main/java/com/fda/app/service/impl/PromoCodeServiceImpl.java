package com.fda.app.service.impl;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
import com.fda.app.service.IPromoCodeService;
import com.fda.app.utility.Utility;

@Service
public class PromoCodeServiceImpl implements IPromoCodeService {

	@Autowired
	private PromoCodeRepository promoCodeRepository;

	@Autowired
	private CustomMapper customMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PromoCodeRepositoyCustom promoCodeRepositoyCustom;

	@Override
	public void addPromoCode(@Valid PromoCodeRequestDto promoCodeRequestDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User currentUser = Utility.getSessionUser(userRepository);
		if (currentUser.getRole() != 2 && currentUser.getRole() != 0) {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
			return;
		}
		if (promoCodeRepository.existsByPromoCode(promoCodeRequestDto.getPromoCode())) {
			apiResponseDtoBuilder.withMessage(Constants.PROMO_CODE_ALREADY_EXIST)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		PromoCode promoCode = customMapper.promoCodeRequestDtoToPromoCode(promoCodeRequestDto);
		promoCode.setCreatedAt(new Date());
		save(promoCode);
		apiResponseDtoBuilder.withMessage(Constants.PROMO_CODE_ADD_SUCCESS).withStatus(HttpStatus.OK)
				.withData(promoCode);
	}

	@Override
	public void getPromoCodeById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		PromoCode promoCode = findById(id);
		if (promoCode == null) {
			apiResponseDtoBuilder.withMessage(Constants.PROMO_CODE_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
			return;
		}
		apiResponseDtoBuilder.withMessage(Constants.PROMO_CODE_INFO).withStatus(HttpStatus.OK).withData(promoCode);
	}

	private void save(PromoCode promoCode) {
		promoCodeRepository.save(promoCode);
	}

	private PromoCode findById(long id) {
		Optional<PromoCode> promoCode = promoCodeRepository.findById(id);
		return promoCode.isPresent() ? promoCode.get() : null;
	}

	@Override
	public void isPromocodeValid(String promoCode, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		PromoCode code = promoCodeRepository.findByPromoCode(promoCode);
		if (code == null) {
			apiResponseDtoBuilder.withMessage("Invalid Promocode").withStatus(HttpStatus.NOT_FOUND);
			return;
		}
		long duration = code.getEndDate().getTime() - new Date().getTime();
		long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		if (diffInMinutes > 0) {
			apiResponseDtoBuilder.withMessage(Constants.PROMO_CODE_INFO).withStatus(HttpStatus.OK).withData(code);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.PROMO_CODE_EXPIRED).withStatus(HttpStatus.OK);
		}

	}

	@Override
	public void getPromoCodeByName(String promoCode, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		PromoCode code = promoCodeRepository.findByPromoCode(promoCode);
		if (code == null) {
			apiResponseDtoBuilder.withMessage("Invalid Promocode").withStatus(HttpStatus.NOT_FOUND);
			return;
		}

		apiResponseDtoBuilder.withMessage(Constants.PROMO_CODE_INFO).withStatus(HttpStatus.OK).withData(code);

	}

	@Override
	public void getPromoCodesListByFilterWithPagination(PromoCodeFilterWithPaginationDto filterWithPagination,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		PaginationDto pagination = promoCodeRepositoyCustom
				.getPromoCodesListByFilterWithPagination(filterWithPagination);
		apiResponseDtoBuilder.withMessage(Constants.DATA_LIST).withStatus(HttpStatus.OK).withData(pagination);

	}

}
