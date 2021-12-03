package com.flab.doorrush.domain.user.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.doorrush.domain.user.dto.UserDto;
import com.flab.doorrush.domain.user.exception.DuplicateUserIdException;
import com.flab.doorrush.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
    public void joinUser_success_test() throws Exception {

        // Given
        UserDto userDto = UserDto.builder()
            .id("yeojae")
            .password("yeojae")
            .name("yeojae")
            .phoneNumber("01012341234")
            .defaultAddress("seoul")
            .email("yeojae@naver.com")
            .build();

        String content = objectMapper.writeValueAsString(userDto);

        // When
        mockMvc.perform(post("/users")
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
    public void joinUser_fail_test() throws Exception {

        // Given
        UserDto userDto = UserDto.builder()
            .id("test1")
            .password("test1")
            .name("test")
            .phoneNumber("01011112222")
            .defaultAddress("seoul")
            .email("aaa@naver.com")
            .build();

        String content = objectMapper.writeValueAsString(userDto);

        // Then
        Exception e = assertThrows(NestedServletException.class,
            () -> {
                // When
                mockMvc.perform(post("/users")
                        .content(content)
                        //json 형식으로 데이터를 보낸다고 명시
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andDo(print());
            });

        assertEquals(DuplicateUserIdException.class, e.getCause().getClass());


    }
}
