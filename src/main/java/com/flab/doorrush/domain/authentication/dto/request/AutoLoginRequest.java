package com.flab.doorrush.domain.authentication.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
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
