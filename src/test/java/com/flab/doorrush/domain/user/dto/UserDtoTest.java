package com.flab.doorrush.domain.user.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

class UserDtoTest {

    @Test
    public void userDto_builder_test() {

        String id = "test1";
        String password = "test1";
        String name = "test1";
        String phoneNumber = "01022223333";
        String defaultAddress = "test1";
        String email = "test1@naver.com";

        // given
        UserDto user = UserDto.builder()
            .id(id)
            .password(password)
            .name(name)
            .phoneNumber(phoneNumber)
            .defaultAddress(defaultAddress)
            .email(email)
            .build();

        // then
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(user.getDefaultAddress()).isEqualTo(defaultAddress);
        assertThat(user.getEmail()).isEqualTo(email);
    }

}