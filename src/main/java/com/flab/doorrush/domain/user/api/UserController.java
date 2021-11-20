package com.flab.doorrush.domain.user.api;

import com.flab.doorrush.domain.user.dto.UserDto;
import com.flab.doorrush.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users/join", method = RequestMethod.POST)
    public void joinUser(UserDto userDto){
        System.out.println(userDto.getUserName());
//        userService.joinUser(userDto);
    }
}
