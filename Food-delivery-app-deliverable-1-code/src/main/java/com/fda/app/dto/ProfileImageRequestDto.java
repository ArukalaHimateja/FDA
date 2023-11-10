package com.fda.app.dto;

public class ProfileImageRequestDto {

	private String profileImageUrl;
	private Long userId;

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userid) {
		this.userId = userid;
	}

}
