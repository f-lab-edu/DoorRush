package com.flab.doorrush.domain.user.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class AutoLoginRequest {

  @NotNull
  private String id;

  @NotNull
  private String password;

  @NotNull
  private boolean isAutoLogin;
}
