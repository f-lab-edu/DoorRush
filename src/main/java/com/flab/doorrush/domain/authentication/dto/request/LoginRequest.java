package com.flab.doorrush.domain.authentication.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginRequest {

  @NotNull
  private String id;

  @NotNull
  private String password;

  @NotNull
  @JsonProperty("autoLogin")
  private boolean isAutoLogin;
}
