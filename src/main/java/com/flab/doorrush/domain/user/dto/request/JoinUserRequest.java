package com.flab.doorrush.domain.user.dto.request;

import com.flab.doorrush.domain.user.domain.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter // getter 메소드를 생성해주는 어노테이션입니다.
@Builder // 디자인 패턴 중 하나인 빌더 패턴 (Builder pattern)를 사용가능하게 해주는 어노테이션입니다.
@Setter // setter 메소드를 생성해주는 어노테이션입니다.
@AllArgsConstructor
public class JoinUserRequest {

  @NotNull
  private String loginId;

  @NotNull
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,18}$",
      message = "비밀번호는 숫자,문자,특수문자를 포함한 6~18로 입력해주세요.")
  private String password;

  @NotNull
  private String name;

  @NotNull
  private String phoneNumber;

  @Email(message = "이메일 형식에 맞게 입력해주세요.")
  private String email;

  public User toEntity() {
    return User.builder()
        .loginId(this.getLoginId())
        .password(this.getPassword())
        .name(this.getName())
        .email(this.getEmail())
        .phoneNumber(this.getPhoneNumber())
        .build();
  }

}
