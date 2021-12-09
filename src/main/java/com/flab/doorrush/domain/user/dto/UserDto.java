package com.flab.doorrush.domain.user.dto;

import com.flab.doorrush.domain.user.domain.User;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter // getter 메소드를 생성해주는 어노테이션입니다.
@Builder // 디자인 패턴 중 하나인 빌더 패턴 (Builder pattern)를 사용가능하게 해주는 어노테이션입니다.
@AllArgsConstructor // 클래스에 모들 필드에 대한 생성자를 자동으로 생성해준다.
public class UserDto {

    @NotNull
    private String id;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    private String email;

    public User toUser(UserDto userDto) {
        return User.builder()
            .id(userDto.getId())
            .password(userDto.getPassword())
            .name(userDto.getName())
            .email(userDto.getEmail())
            .phoneNumber(userDto.getPhoneNumber())
            .build();
    }

}
