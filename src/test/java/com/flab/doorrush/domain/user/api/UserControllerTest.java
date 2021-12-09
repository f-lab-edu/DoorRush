package com.flab.doorrush.domain.user.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.doorrush.domain.user.dto.LoginDto;
import com.flab.doorrush.domain.user.dto.UserDto;
import com.flab.doorrush.domain.user.exception.DuplicatedUserIdException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

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


  @Test // @Test : 테스트가 수행되는 메소드를 가르킨다.
  public void joinUserSuccessTest() throws Exception {

    // Given
    UserDto userDto = UserDto.builder()
        .id("yeojae")
        .password("yeojae")
        .name("yeojae")
        .phoneNumber("01012341234")
        .email("yeojae@naver.com")
        .build();

    String content = objectMapper.writeValueAsString(userDto);

    // When
    mockMvc.perform(post("/users/")
            .content(content)
            //json 형식으로 데이터를 보낸다고 명시
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value("yeojae"))
        .andExpect(jsonPath("$.password").value("yeojae"))
        .andExpect(jsonPath("$.name").value("yeojae"))
        .andExpect(jsonPath("$.phoneNumber").value("01012341234"))
        .andExpect(jsonPath("$.email").value("yeojae@naver.com"));

  }

  @Test
  public void joinUserFailTest() throws Exception {

    // Given
    UserDto userDto = UserDto.builder()
        .id("test1")
        .password("test1")
        .name("test")
        .phoneNumber("01011112222")
        .email("aaa@naver.com")
        .build();

    String content = objectMapper.writeValueAsString(userDto);

    // Then
    Exception e = assertThrows(NestedServletException.class,
        () -> {
          // When
          mockMvc.perform(post("/users/")
                  .content(content)
                  //json 형식으로 데이터를 보낸다고 명시
                  .contentType(MediaType.APPLICATION_JSON)
                  .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isCreated())
              .andDo(print());
        });

    assertEquals(DuplicatedUserIdException.class, e.getCause().getClass());
  }

  @Test
  public void loginSuccessTest() throws Exception {
    // Given
    LoginDto loginDto = new LoginDto("test1", "test1pw");
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
  public void loginFailTest() throws Exception {
    // Given
    LoginDto loginDto = new LoginDto("test1", "test12345567pw");
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
  public void logoutTest() throws Exception {
    // Given
    LoginDto loginDto = new LoginDto("test1", "test1pw");
    MockHttpSession mockHttpSession = new MockHttpSession();
    mockHttpSession.setAttribute("login", "yes");

    // When
    mockMvc.perform(post("/users/logout")
            .session(mockHttpSession)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect(status().isOk());
  }
}
