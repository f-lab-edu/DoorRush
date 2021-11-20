package com.flab.doorrush.domain.user.api;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.doorrush.domain.user.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// @RunWith : JUnit 프레임워크가 테스트를 실행할 시 테스트 실행방법을 확장할 때 쓰는 어노테이션
@RunWith(SpringRunner.class)
/*
 * @WebMvcTest : MVC를 위한 테스트, 컨트롤러가 예상대로 동작하는지 테스트하는데 사용됩니다. Web과 관련된 어노테이션만 스캔합니다.
 * @Controller, @ControllerAdvice, @JsonComponent, Converter, GenericConverter, Filter, HandlerInterceptor, WebMvcConfigurer, HandlerMethodArgumentResolver
*/
@WebMvcTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserController userController;

    @Test // @Test : 테스트가 수행되는 메소드를 가르킨다.
    public void joinUser() throws Exception {

        String content = objectMapper.writeValueAsString(UserDto.builder()
            .userId("yeddd")
            .password("1234")
            .userName("yeonjae")
            .userPhoneNumber("010222")
            .userDefaultAddr("경기도")
            .userEmail("aaa@naver.com")
            .build());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/join")
                    .content(content)
                    //json 형식으로 데이터를 보낸다고 명시
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print());
    }
}
