package com.flab.doorrush.domain.user.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.doorrush.domain.user.dto.LoginDto;
import com.flab.doorrush.domain.user.dto.request.ChangePasswordRequest;
import com.flab.doorrush.domain.user.dto.request.JoinUserRequest;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

/* @RunWith : JUnit 프레임워크가 테스트를 실행할 시 테스트 실행방법을 확장할 때 쓰는 어노테이션
 * @WebMvcTest : MVC를 위한 테스트, 컨트롤러가 예상대로 동작하는지 테스트하는데 사용됩니다. Web과 관련된 다음 어노테이션만 스캔합니다.
        (@Controller, @ControllerAdvice, @JsonComponent, Converter, GenericConverter, Filter, HandlerInterceptor, WebMvcConfigurer, HandlerMethodArgumentResolver)
 * @AutoConfigureMockMvc : @WebMvcTest와 비슷하지만 가장 큰 차이점은 컨트롤러 뿐만 아니라 테스트 대상이 아닌 @Service나 @Repository가 붙은 객체들도 모두 메모리에 올립니다.*/
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  private static ValidatorFactory factory;
  private static Validator validator;

  @BeforeAll
  public static void init() {
    factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @AfterAll
  public static void close() {
    factory.close();
  }

  @Test // @Test : 테스트가 수행되는 메소드를 가르킨다.
  @DisplayName("회원가입 성공 테스트 상태값 201을 반환한다.")
  public void joinUserSuccessTest() throws Exception {
    // Given
    JoinUserRequest joinUserRequest = JoinUserRequest.builder()
        .loginId("yeojae")
        .password("yeojae123!")
        .name("yeojae")
        .phoneNumber("01012341234")
        .email("yeojae@naver.com")
        .build();

    String content = objectMapper.writeValueAsString(joinUserRequest);

    // When
    mockMvc.perform(post("/users/")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.user.userSeq").isNotEmpty())
        .andExpect(jsonPath("$.user.loginId").value("yeojae"))
        .andExpect(jsonPath("$.user.name").value("yeojae"))
        .andExpect(jsonPath("$.user.phoneNumber").value("01012341234"))
        .andExpect(jsonPath("$.user.email").value("yeojae@naver.com"));
  }

  @Test
  @DisplayName("회원가입 실패 테스트 상태값 400을 발생시킨다.")
  public void joinUserFailTest() throws Exception {
    // Given
    JoinUserRequest joinUserRequest = JoinUserRequest.builder()
        .loginId("test1")
        .password("test1")
        .name("test")
        .phoneNumber("01011112222")
        .email("aaanaver.com")
        .build();

    String content = objectMapper.writeValueAsString(joinUserRequest);

    // When
    mockMvc.perform(post("/users/")
            .content(content)
            //json 형식으로 데이터를 보낸다고 명시
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isBadRequest())
        .andDo(print());

  }

  @Test
  @DisplayName("로그인 성공 테스트 상태값 200을 발생시킨다.")
  public void loginSuccessTest() throws Exception {
    // Given
    LoginDto loginDto = new LoginDto("test6", "test6pw");
    String content = objectMapper.writeValueAsString(loginDto);
    MockHttpSession mockHttpSession = new MockHttpSession();

    // When
    mockMvc.perform(post("/users/login").content(content)
            .session(mockHttpSession)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그인 실패 테스트 상태값 404를 발생시킨다.")
  public void loginFailTest() throws Exception {
    // Given
    LoginDto loginDto = new LoginDto("test6", "test12345567pw");
    String content = objectMapper.writeValueAsString(loginDto);
    MockHttpSession mockHttpSession = new MockHttpSession();

    // When
    mockMvc.perform(post("/users/login").content(content)
            .session(mockHttpSession)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("중복 로그인 실패 테스트 상태값 403을 발생시킨다.")
  public void loginFailDuplicatedLoginTest() throws Exception {
    // Given
    LoginDto loginDto = new LoginDto("test6", "test6pw");
    String content = objectMapper.writeValueAsString(loginDto);
    MockHttpSession mockHttpSession = new MockHttpSession();

    mockMvc.perform(post("/users/login").content(content)
            .session(mockHttpSession)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());

    // When
    mockMvc.perform(post("/users/login").content(content)
            .session(mockHttpSession)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(status().isForbidden());

  }

  @Test
  @DisplayName("로그아웃 성공 테스트 상태값 200을 발생시킨다.")
  public void logoutSuccessTest() throws Exception {
    // Given
    MockHttpSession mockHttpSession = new MockHttpSession();
    mockHttpSession.setAttribute("loginId", "yes");

    // When
    mockMvc.perform(post("/users/logout")
            .session(mockHttpSession)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그아웃 실패 테스트 상태값 404를 발생시킨다.")
  public void logoutFailTest() throws Exception {
    // Given
    MockHttpSession mockHttpSession = new MockHttpSession();

    // When
    mockMvc.perform(post("/users/logout")
            .session(mockHttpSession)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("비밀번호 변경 성공 테스트 200을 반환")
  public void changePasswordSuccessTest() throws Exception {
    // Given
    String originPassword = "test6pw";
    String newPassword = "test6pwChange!";
    ChangePasswordRequest changePasswordRequest = ChangePasswordRequest.builder()
        .originPassword(originPassword).newPassword(newPassword)
        .build();

    String content = objectMapper.writeValueAsString(changePasswordRequest);

    // When
    mockMvc.perform(patch("/users/25/password")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("비밀번호 변경 실패 테스트 404을 반환 (기존 비밀번호 불일치)")
  public void changePasswordFailTest() throws Exception {
    // Given
    String originPassword = "test6pwFail";
    String newPassword = "test6pwChange!";
    ChangePasswordRequest changePasswordRequest = ChangePasswordRequest.builder()
        .originPassword(originPassword).newPassword(newPassword)
        .build();

    String content = objectMapper.writeValueAsString(changePasswordRequest);

    // When
    mockMvc.perform(patch("/users/25/password")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("비밀번호 유효성 검사 실패 테스트")
  public void passwordValidFailTest() {
    // Given
    String originPassword = "test6pw";
    String newPassword = "t1!a";

    // When
    Set<ConstraintViolation<ChangePasswordRequest>> violations = validator.validate(
        ChangePasswordRequest.builder()
            .originPassword(originPassword).newPassword(newPassword)
            .build());

    // Then
    assertThat(violations).isNotEmpty();
    violations.forEach(
        error -> {
          assertThat(error.getMessage().contains("비밀번호는 숫자,문자,특수문자를 포함한 6~18로 입력해주세요."));
        });
  }

  @Test
  @DisplayName("비밀번호 유효성 검사 성공 테스트")
  public void passwordValidSuccessTest() {
    // Given
    String originPassword = "test6pw";
    String newPassword = "test6pw123!!!";

    // When
    Set<ConstraintViolation<ChangePasswordRequest>> violations = validator.validate(
        ChangePasswordRequest.builder()
            .originPassword(originPassword).newPassword(newPassword)
            .build());

    // Then
    assertThat(violations).isEmpty();
  }

}
