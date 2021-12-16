package com.flab.doorrush.domain.user.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginDto {

  @NotNull
  private String id;

  @NotNull
  private String password;

}
