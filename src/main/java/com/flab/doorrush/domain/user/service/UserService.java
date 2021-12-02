package com.flab.doorrush.domain.user.service;

import com.flab.doorrush.domain.user.dao.UserMapper;
import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.dto.UserDto;
import com.flab.doorrush.domain.user.exception.DuplicateUserIdException;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
// @RequiredArgsConstructor : 초기화되지 않은 final 필드를 매개변수로 받는 생성자를 생성하는 어노테이션입니다.
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public void joinUser(UserDto userDto) {
        User user = userDto.toUser(userDto);
        joinUser(user);
    }

    public void joinUser(User user) {
        boolean isDuplicate = isDuplicatedId(user.getId());
        if (isDuplicate) {
            throw new DuplicateUserIdException("이미 사용중인 아이디입니다.");
        }
        int insertResult = userMapper.insertUser(user);
        if (insertResult != 1) {
            throw new DuplicateUserIdException("회원가입이 실패하였습니다.");
        }
    }

    public boolean isDuplicatedId(String id) {
        return userMapper.getCountId(id) == 1;
    }

    public UserDto getUserById(String userId) {

        Optional<User> user = userMapper.getUserById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("회원정보가 없습니다.");
        }
        return user.get().toUserDto(user.get());
    }
}
