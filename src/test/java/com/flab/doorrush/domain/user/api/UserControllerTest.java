package com.flab.doorrush.domain.user.api;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.doorrush.domain.user.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void controllerTest() throws Exception {

        String content = objectMapper.writeValueAsString(UserDto.builder()
            .userId("yeonjae")
            .password("1234")
            .userName("yeonjae")
            .userPhoneNumber("010222")
            .userDefaultAddr("경기도")
            .userEmail("aaa@naver.com")
            .build());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/join")
                    .content(content)
                    //json 형식으로 데이터를 보낸다고 명시
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(print());
    }
}