package com.flab.doorrush.domain.user.api;

import com.flab.doorrush.domain.user.dto.UserDto;
import com.flab.doorrush.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
// @RequiredArgsConstructor : 초기화되지 않은 final 필드를 매개변수로 받는 생성자를 생성하는 어노테이션입니다.
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> joinUser(@RequestBody UserDto userDto) {
        userService.joinUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
}