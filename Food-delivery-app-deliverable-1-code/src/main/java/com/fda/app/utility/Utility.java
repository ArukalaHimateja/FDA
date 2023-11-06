package com.fda.app.utility;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fda.app.dto.PaginationDataDto;
import com.fda.app.dto.PaginationDto;
import com.fda.app.model.User;
import com.fda.app.repository.UserRepository;

public class Utility {

	public static String generateRandomPassword(int len) {
		String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String Small_chars = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";

		String values = Capital_chars + Small_chars + numbers ;

		Random rndm_method = new Random();

		String password = "";

		for (int i = 0; i < len; i++) {

			char ch = values.charAt(rndm_method.nextInt(values.length()));
			password += ch;

		}
		return password;
	}

	public static int generateOTP(int length) {
		String numbers = "1234567890";
		Random random = new Random();
		String otp = new String();

		for (int i = 0; i < length; i++) {
			otp += numbers.charAt(random.nextInt(numbers.length()));
		}

		return Integer.parseInt(otp);
	}

	public static PaginationDataDto getPaginationData(long totalCounts, PaginationDto paginationDto) {
		PaginationDataDto paginationDataDto = new PaginationDataDto();
		int totalPages = (int) Math.ceil((double) totalCounts / (double) paginationDto.getPerPage());
		int from = paginationDto.getCurrentPage() - 1;
		int to = paginationDto.getPerPage();
		paginationDataDto.setTotalPages(totalPages);
		paginationDataDto.setFrom(from);
		paginationDataDto.setTo(to);
		return paginationDataDto;
	}

	public static <T, U> List<U> convertStringListToIntegerList(List<T> listOfString, Function<T, U> function) {
		return listOfString.stream().map(function).collect(Collectors.toList());
	}

	public static String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	public static String addANDOrOR(boolean flag) {
		String condition = "";
		if (flag) {
			condition = " AND";
		}
		return condition;
	}
	
	public static String addOR(boolean flag) {
		String condition = "";
		if (flag) {
			condition = " OR";
		}
		return condition;
	}

	public static String addWhere(boolean flag) {
		String condition = "";
		if (flag) {
			condition = " where";
		}
		return condition;
	}

	public static User getSessionUser(UserRepository userRepository) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUsername = authentication.getName();
		return userRepository.findByEmail(currentUsername);
	}

	public static Date getFormatedDateFromDate(Date date, int hourOfDay, int minute) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.AM_PM, 0);
			calendar.set(Calendar.HOUR, hourOfDay);
			calendar.set(Calendar.MINUTE, minute);
			calendar.set(Calendar.SECOND, 0);
			return calendar.getTime();
		} catch (Exception e) {
			return null;
		}
	}

//	public static Boolean isValidAadhaarNumber(String str) {
//
//		String regex = "^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$";
//		Pattern p = Pattern.compile(regex);
//		if (str == null) {
//			return false;
//		}
//		Matcher m = p.matcher(str);
//		return m.matches();
//	}
//
//	public static boolean isValidGSTNo(String str) {
//		String regex = "^[0-9]{2}[A-Z]{5}[0-9]{4}" + "[A-Z]{1}[1-9A-Z]{1}" + "Z[0-9A-Z]{1}$";
//		Pattern p = Pattern.compile(regex);
//		if (str == null) {
//			return false;
//		}
//		Matcher m = p.matcher(str);
//		return m.matches();
//	}
}
