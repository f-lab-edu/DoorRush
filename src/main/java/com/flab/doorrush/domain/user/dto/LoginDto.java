package com.flab.doorrush.domain.user.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class LoginDto {

  @NotNull
  private String id;

  @NotNull
  private String password;

}
