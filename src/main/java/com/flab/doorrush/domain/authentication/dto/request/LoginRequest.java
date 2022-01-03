package com.flab.doorrush.domain.authentication.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

  @NotNull
  private String id;

  @NotNull
  private String password;

}
