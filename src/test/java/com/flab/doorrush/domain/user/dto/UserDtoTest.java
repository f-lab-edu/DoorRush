package com.flab.doorrush.domain.user.dto;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class UserDtoTest {

    @Test
    public void userDtoTest() {
        String userId = "testID";
        String password = "testPW";
        String name = "Yeonjae";
        String phoneNo = "01022223333";
        String addr = "aaa";
        String email = "yj@naver.com";

        UserDto user = new UserDto(userId, password, name, phoneNo, addr, email);

        //then
        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getUserName()).isEqualTo(name);
        assertThat(user.getUserPhoneNumber()).isEqualTo(phoneNo);
        assertThat(user.getUserDefaultAddr()).isEqualTo(addr);
        assertThat(user.getUserEmail()).isEqualTo(email);
    }

}