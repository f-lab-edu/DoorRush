package com.flab.doorrush.domain.user.dto.request;

import com.flab.doorrush.domain.user.domain.User;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter // getter 메소드를 생성해주는 어노테이션입니다.
@Builder // 디자인 패턴 중 하나인 빌더 패턴 (Builder pattern)를 사용가능하게 해주는 어노테이션입니다.
@AllArgsConstructor
public class JoinUserRequest {

    @NotNull
    private String loginId;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    private String email;

    public static User toUser(JoinUserRequest joinUserRequest) {
        return User.builder()
            .loginId(joinUserRequest.getLoginId())
            .password(joinUserRequest.getPassword())
            .name(joinUserRequest.getName())
            .email(joinUserRequest.getEmail())
            .phoneNumber(joinUserRequest.getPhoneNumber())
            .build();
    }

}
