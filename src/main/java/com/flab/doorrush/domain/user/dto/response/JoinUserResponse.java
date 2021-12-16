package com.flab.doorrush.domain.user.dto.response;

import com.flab.doorrush.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter // getter 메소드를 생성해주는 어노테이션입니다.
@Builder // 디자인 패턴 중 하나인 빌더 패턴 (Builder pattern)를 사용가능하게 해주는 어노테이션입니다.
@AllArgsConstructor
public class JoinUserResponse {

  private User user;

  public static JoinUserResponse from(User user) {
    return JoinUserResponse.builder()
        .user(user)
        .build();
  }

}
