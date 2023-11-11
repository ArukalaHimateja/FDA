package com.fda.app.mapper;

import com.fda.app.dto.LoginResponseDto;
import com.fda.app.dto.UserListResponseDto;
import com.fda.app.dto.UserRequestDto;
import com.fda.app.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-08T00:04:57-0600",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230814-2020, environment: Java 17.0.8.1 (Eclipse Adoptium)"
)
@Component
public class CustomMapperImpl implements CustomMapper {

    @Override
    public User userRequestDtoToUser(UserRequestDto userRequestDto) {
        if ( userRequestDto == null ) {
            return null;
        }

        User user = new User();

        user.setFullName( userRequestDto.getFullName() );
        user.setAddress( userRequestDto.getAddress() );
        user.setEmail( userRequestDto.getEmail() );
        user.setPassword( userRequestDto.getPassword() );
        user.setMobileNumber( userRequestDto.getMobileNumber() );

        return user;
    }

    @Override
    public List<UserListResponseDto> userListToUserListResponseDtoList(List<User> userList) {
        if ( userList == null ) {
            return null;
        }

        List<UserListResponseDto> list = new ArrayList<UserListResponseDto>( userList.size() );
        for ( User user : userList ) {
            list.add( userToUserListResponseDto( user ) );
        }

        return list;
    }

    @Override
    public LoginResponseDto userToLoginResponseDto(User checkUser) {
        if ( checkUser == null ) {
            return null;
        }

        LoginResponseDto loginResponseDto = new LoginResponseDto();

        loginResponseDto.setId( checkUser.getId() );
        loginResponseDto.setCreatedAt( checkUser.getCreatedAt() );
        loginResponseDto.setUpdatedAt( checkUser.getUpdatedAt() );
        loginResponseDto.setFullName( checkUser.getFullName() );
        loginResponseDto.setEmail( checkUser.getEmail() );
        loginResponseDto.setPassword( checkUser.getPassword() );
        loginResponseDto.setMobileNumber( checkUser.getMobileNumber() );
        loginResponseDto.setAddress( checkUser.getAddress() );
        loginResponseDto.setRole( checkUser.getRole() );
        loginResponseDto.setStripeUserId( checkUser.getStripeUserId() );
        loginResponseDto.setProfileImage( checkUser.getProfileImage() );
        loginResponseDto.setActive( checkUser.getActive() );
        loginResponseDto.setVerify( checkUser.getVerify() );

        return loginResponseDto;
    }

    protected UserListResponseDto userToUserListResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserListResponseDto userListResponseDto = new UserListResponseDto();

        userListResponseDto.setEmail( user.getEmail() );
        if ( user.getVerify() != null ) {
            userListResponseDto.setVerify( user.getVerify() );
        }
        userListResponseDto.setId( user.getId() );
        userListResponseDto.setCreatedAt( user.getCreatedAt() );
        userListResponseDto.setUpdatedAt( user.getUpdatedAt() );
        userListResponseDto.setFullName( user.getFullName() );
        userListResponseDto.setMobileNumber( user.getMobileNumber() );
        userListResponseDto.setProfileImage( user.getProfileImage() );
        userListResponseDto.setRole( user.getRole() );
        if ( user.getActive() != null ) {
            userListResponseDto.setActive( user.getActive() );
        }

        return userListResponseDto;
    }
}
