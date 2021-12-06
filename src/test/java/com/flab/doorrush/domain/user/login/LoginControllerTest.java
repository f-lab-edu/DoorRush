package com.flab.doorrush.domain.user.login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.doorrush.domain.user.dto.LoginDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  protected MockHttpSession session;


  // Given
  @BeforeEach // @Test가 붙은 테스트 메서드가 실행되기 전에 먼저 @BeforeEach가 붙은 메서드가 실행되도록 하는 어노테이션
  public void setUp() {
    session = new MockHttpSession();
    session.setAttribute("login", "yes");
  }


  @AfterEach // @Test가 붙은 테스트 메서드가 실행된 후에 @AfterEach가 붙은 메서드가 실행되도록 하는 어노테이션
  public void clean() {
    session.clearAttributes();
  }


  @Test
  public void loginControllerTest() throws Exception {
    LoginDto loginDto = new LoginDto("test1", "test1pw");
    String content = objectMapper.writeValueAsString(loginDto);

    // When
    mockMvc.perform(post("/users/login").content(content)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(status().isOk());
  }
}
