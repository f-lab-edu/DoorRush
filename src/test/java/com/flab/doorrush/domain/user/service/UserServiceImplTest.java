package com.flab.doorrush.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*
* @SpringBootTest : SpringBoot 기능을 제공해주며 SpringBoot 통합테스트를 할 때 사용되는 주석
* @SpringBootTest 어노테이션은 Spring Main Application(@SpringBootApplication)을 찾아가 하위의 모든 Bean을 Scan
* */
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void serviceTest() throws Exception {
        boolean checkId = userService.getUserById("testID44444");
        assertThat(checkId).isEqualTo(true);
    }
}