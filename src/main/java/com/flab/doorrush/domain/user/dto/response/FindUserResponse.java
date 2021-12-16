package com.flab.doorrush.domain.user.dto.response;

import com.flab.doorrush.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FindUserResponse {

  private User user;

  public static FindUserResponse from(User user) {
    return FindUserResponse.builder()
        .user(user)
        .build();
  }
}
