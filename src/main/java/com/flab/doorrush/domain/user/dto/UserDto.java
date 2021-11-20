package com.flab.doorrush.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
public class UserDto {

    private String userId;
    private String password;
    private String userName;
    private String userPhoneNumber;
    private String userDefaultAddr;
    private String userEmail;

    public UserDto(String userId, String password, String userName, String userPhoneNumber,
        String userDefaultAddr, String userEmail) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.userDefaultAddr = userDefaultAddr;
        this.userEmail = userEmail;
    }
}
