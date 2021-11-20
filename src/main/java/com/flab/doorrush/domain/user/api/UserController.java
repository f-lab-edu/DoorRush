package com.flab.doorrush.domain.user.api;

import com.flab.doorrush.domain.user.dto.UserDto;
import com.flab.doorrush.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 메소드
    @PostMapping("/join")
    public ResponseEntity joinUser(@RequestBody UserDto userDto) throws Exception {
        boolean checkId = userService.getUserById(userDto.getUserId());
        if (checkId) {
            userService.joinUser(userDto);
        } else {
            throw new Exception("아이디가 존재합니다.");
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
