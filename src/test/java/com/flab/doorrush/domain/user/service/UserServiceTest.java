package com.flab.doorrush.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.flab.doorrush.domain.authentication.exception.InvalidPasswordException;
import com.flab.doorrush.domain.user.dao.UserMapper;
import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.dto.request.ChangePasswordRequest;
import com.flab.doorrush.domain.user.dto.request.JoinUserRequest;
import com.flab.doorrush.domain.user.dto.response.FindUserResponse;
import com.flab.doorrush.domain.user.exception.DuplicatedUserIdException;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * @SpringBootTest : SpringBoot 기능을 제공해주며 SpringBoot 통합테스트를 할 때 사용되는 주석
 * @SpringBootTest 어노테이션은 Spring Main Application(@SpringBootApplication)을 찾아가 하위의 모든 Bean을 Scan
 * */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  UserMapper userMapper;

  @Mock
  PasswordEncoder passwordEncoder;

  @InjectMocks
  UserService userService;

  User user;
  User user2;
  JoinUserRequest joinUserRequest;

  @BeforeEach
  void setup() {
    user = User.builder()
        .userSeq(1L)
        .loginId("test1")
        .password("test1pw")
        .name("testName")
        .phoneNumber("01011112222")
        .email("testEmail@naver.com")
        .build();

    joinUserRequest = JoinUserRequest.builder()
        .loginId("test1")
        .password("test1pw")
        .name("testName")
        .phoneNumber("01011112222")
        .email("testEmail@naver.com")
        .build();

    user2 =User.builder()
        .userSeq(6L)
        .loginId("test6")
        .password("test6pw")
        .phoneNumber("01011112222")
        .email("test6@email.com")
        .build();
  }


  @Test
  @DisplayName("아이디로 사용자 정보 조회결과가 예상한 결과값과 동일하다.")
  public void getUserByIdSuccessTest() {
    // Given
    given(userMapper.selectUserById("test1")).willReturn(Optional.of(user));

    // When
    FindUserResponse findUserResponse = userService.getUserById("test1");

    // Then
    assertThat(findUserResponse.getUser().getLoginId()).isEqualTo(user.getLoginId());
    assertThat(findUserResponse.getUser().getName()).isEqualTo(user.getName());
  }

  @Test
  @DisplayName("아이디로 사용자 정보 조회 후 없으면 예외를 발생시킨다.")
  public void getUserByIdFailTest() {
    // Given
    given(userMapper.selectUserById("test1")).willReturn(Optional.empty());

    // When, Then
    assertThrows(UserNotFoundException.class,
        () -> userService.getUserById("test1"));
  }

  @Test
  @DisplayName("아이디 중복 테스트 중복된 아이디일 경우 예외를 발생시킨다.")
  public void isDuplicatedIdFailTest() {
    // Given
    given(userMapper.selectUserById(joinUserRequest.getLoginId())).willReturn(Optional.of(user));

    // Given, When, Then
    assertThrows(DuplicatedUserIdException.class,
        () -> userService.joinUser(joinUserRequest));
  }

  @Test
  @DisplayName("사용자 회원가입 테스트 정상적으로 회원가입된다.")
  public void joinUserSuccessTest() {
    // Given
    given(userMapper.selectUserById(joinUserRequest.getLoginId()))
        .willReturn(Optional.empty())
        .willReturn(Optional.of(user));

    // When
    userService.joinUser(joinUserRequest);

    // Then
    FindUserResponse user = userService.getUserById(joinUserRequest.getLoginId());
    assertThat(user.getUser().getUserSeq()).isNotNull();
    assertThat(user.getUser().getLoginId()).isEqualTo(joinUserRequest.getLoginId());
    assertThat(user.getUser().getName()).isEqualTo(joinUserRequest.getName());

  }

  @Test
  @DisplayName("비밀번호 암호화 테스트")
  public void passwordEncryptTest() {
    // Given
    given(passwordEncoder.encode(anyString())).willReturn("encoderPasswoed");
    given(passwordEncoder.matches("12345", "encoderPasswoed")).willReturn(true);
    String password = "12345";

    // When
    String encodePassword = passwordEncoder.encode(password);

    // Then
    assertNotEquals(password, encodePassword);
    assertTrue(passwordEncoder.matches(password, encodePassword));
  }

  @Test
  @DisplayName("아이디로 회원 정보 조회 후 암호화된 비밀번호 매칭 테스트")
  public void passwordEncryptWhtigetUserByIdTest() {
    // Given
    given(userMapper.selectUserById("test1")).willReturn(Optional.of(user));
    given(passwordEncoder.matches(user.getPassword(), user.getPassword())).willReturn(true);
    String reqLoginId = "test1";

    // When
    FindUserResponse findUserResponse = userService.getUserById(reqLoginId);

    // Then
    assertTrue(passwordEncoder.matches(user.getPassword(), findUserResponse.getUser().getPassword()));
  }

  @Test
  @DisplayName("비밀번호 변경 성공 테스트")
  public void changePasswordSuccessTest() {
    // Given
    User testUser =User.builder()
        .userSeq(6L)
        .loginId("test6")
        .password("test6pw")
        .phoneNumber("01011112222")
        .email("test6@email.com")
        .build();

    Long userSeq = testUser.getUserSeq();
    String originPassword = testUser.getPassword();
    String newPassword = "test6pwChange";
    ChangePasswordRequest request = ChangePasswordRequest.builder()
        .originPassword(originPassword).newPassword(newPassword)
        .build();

    given(userMapper.selectUserByUserSeq(userSeq)).willReturn(Optional.of(testUser));
    given(passwordEncoder.matches(request.getOriginPassword(), testUser.getPassword())).willReturn(true);
    given(passwordEncoder.encode(request.getNewPassword())).willReturn(request.getNewPassword());
    given(userMapper.updatePassword(any())).willReturn(1);

    // When
    boolean isUpdate = userService.changePassword(userSeq, request);

    // Then
    assertTrue(isUpdate);
  }

  @Test
  @DisplayName("비밀번호 변경 실패 테스트 (기존 비밀번호 불일치)")
  public void changePasswordFailTest() {
    // Given
    Long userSeq = 6L;
    String originPassword = "test6pwfail";
    String newPassword = "test6pwChange";
    ChangePasswordRequest request = ChangePasswordRequest.builder()
        .originPassword(originPassword).newPassword(newPassword)
        .build();
    given(userMapper.selectUserByUserSeq(6L)).willReturn(Optional.of(user2));
    given(passwordEncoder.matches(request.getOriginPassword(), user2.getPassword())).willReturn(false);

    // Then
    assertThrows(InvalidPasswordException.class,
        // When
        () -> userService.changePassword(userSeq, request));
  }

}
