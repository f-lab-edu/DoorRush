package com.flab.doorrush.domain.authentication.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.doorrush.domain.authentication.dto.request.LoginRequest;
import com.flab.doorrush.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

  @Autowired
  UserService userService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;


  @Test
  @DisplayName("로그인 성공 테스트 상태값 200을 발생시킨다.")
  public void loginSuccessTest() throws Exception {
    // Given
    LoginRequest loginRequest = LoginRequest.builder()
        .id("test6")
        .password("test6pw")
        .isAutoLogin(false).build();
    String content = objectMapper.writeValueAsString(loginRequest);
    MockHttpSession mockHttpSession = new MockHttpSession();

    // When
    mockMvc.perform(post("/login").content(content)
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
    LoginRequest loginRequest = LoginRequest.builder()
        .id("test6")
        .password("test12345567pw")
        .isAutoLogin(false).build();
    String content = objectMapper.writeValueAsString(loginRequest);
    MockHttpSession mockHttpSession = new MockHttpSession();

    // When
    mockMvc.perform(post("/login").content(content)
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
    LoginRequest loginRequest = LoginRequest.builder()
        .id("test6")
        .password("test6pw")
        .isAutoLogin(false).build();
    String content = objectMapper.writeValueAsString(loginRequest);
    MockHttpSession mockHttpSession = new MockHttpSession();

    mockMvc.perform(post("/login").content(content)
            .session(mockHttpSession)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());

    // When
    mockMvc.perform(post("/login").content(content)
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
    mockMvc.perform(post("/logout")
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
    mockMvc.perform(post("/logout")
            .session(mockHttpSession)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(status().isNotFound());
  }


  @Test
  @DisplayName("자동 로그인 true 테스트 value 가 UserSeq 인 AUTOLOGIN 쿠키 추가 후 상태값 200을 발생시킨다.")
  public void autoLoginTrueTest() throws Exception {
    // Given
    LoginRequest loginRequest = LoginRequest.builder()
        .id("test6")
        .password("test6pw")
        .isAutoLogin(true)
        .build();
    String content = objectMapper.writeValueAsString(loginRequest);
    MockHttpSession mockHttpSession = new MockHttpSession();

    // When
    mockMvc.perform(post("/login").content(content)
            .session(mockHttpSession)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(cookie().exists("AUTOLOGIN"))
        .andExpect(cookie().value("AUTOLOGIN", "tWMTd2+86EqA8iNfApsQiQ=="))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("자동 로그인 flase 테스트 일반 로그인 처리 후 상태값 200을 발생시킨다.")
  public void autoLoginFalseTest() throws Exception {
    // Given
    LoginRequest loginRequest = LoginRequest.builder()
        .id("test6")
        .password("test6pw")
        .isAutoLogin(false).build();
    String content = objectMapper.writeValueAsString(loginRequest);
    MockHttpSession mockHttpSession = new MockHttpSession();

    // When
    mockMvc.perform(post("/login").content(content)
            .session(mockHttpSession)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(status().isOk());
  }
}