package com.flab.doorrush.domain.authentication.aspect;

import static com.flab.doorrush.domain.authentication.service.AuthenticationService.LOGIN_SESSION;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.flab.doorrush.domain.authentication.exception.AuthenticationCredentialsNotFoundException;
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
class CheckLoginAspectTest {

  @Autowired
  MockMvc mockMvc;


  @Test
  @DisplayName("@CheckLogin 적용 메소드 성공 테스트 checkLogin() 수행 후 요청 처리.")
  public void checkLoginSuccessTest() throws Exception {
    // Given

    MockHttpSession mockHttpSession = new MockHttpSession();
    mockHttpSession.setAttribute(LOGIN_SESSION, "test1");

    // When
    mockMvc.perform(post("/checklogintest")
            .session(mockHttpSession)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("@CheckLogin 적용 메소드 실패 테스트 checkLogin() 수행 후"
      + " AuthenticationCredentialsNotFoundException 발생, HttpStatus.FORBIDDEN 을 반환한다.")
  public void checkLoginFailTest() throws Exception {
    // Given
    MockHttpSession mockHttpSession = new MockHttpSession();

    // When
    mockMvc.perform(post("/checklogintest")
            .session(mockHttpSession)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(result -> assertTrue(
            result.getResolvedException().getClass().isAssignableFrom(
                AuthenticationCredentialsNotFoundException.class)))
        .andExpect(status().isForbidden());
  }
}