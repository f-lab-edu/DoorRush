package com.flab.doorrush.domain.user.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ChangePasswordRequest {

  @NotNull
  private String originPassword;

  @NotNull
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,18}$",
      message = "비밀번호는 숫자,문자,특수문자를 포함한 6~18로 입력해주세요.")
  private String newPassword;

}
