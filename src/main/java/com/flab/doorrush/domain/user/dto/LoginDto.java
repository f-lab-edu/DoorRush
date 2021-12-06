package com.flab.doorrush.domain.user.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

  @NotNull
  private String id;

  @NotNull
  private String password;

  public LoginDto(String id, String password){
    this.id=id;
    this.password = password;
  }
}
