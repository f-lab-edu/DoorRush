package com.flab.doorrush.domain.user.api;

import com.flab.doorrush.domain.user.dto.UserDto;
import com.flab.doorrush.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

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
