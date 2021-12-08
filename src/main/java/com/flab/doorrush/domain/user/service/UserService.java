package com.flab.doorrush.domain.user.service;

import com.flab.doorrush.domain.user.dao.UserMapper;
import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.dto.request.JoinUserRequest;
import com.flab.doorrush.domain.user.dto.response.FindUserResponse;
import com.flab.doorrush.domain.user.dto.response.JoinUserResponse;
import com.flab.doorrush.domain.user.exception.DuplicatedUserIdException;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
// @RequiredArgsConstructor : 초기화되지 않은 final 필드를 매개변수로 받는 생성자를 생성하는 어노테이션입니다.
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public JoinUserResponse joinUser(JoinUserRequest joinUserRequest) {
        User user = JoinUserRequest.toUser(joinUserRequest);

        boolean isDuplicated = isDuplicatedId(user.getLoginId());
        if (isDuplicated) {
            throw new DuplicatedUserIdException("이미 사용중인 아이디입니다.");
        }
        userMapper.insertUser(user);
        return JoinUserResponse.createJoinUserResponse(user);
    }


    public boolean isDuplicatedId(String id) {
        return userMapper.getCountById(id);
    }

    public FindUserResponse getUserById(String userId) {

        User user = userMapper.getUserById(userId)
            .orElseThrow(() -> new UserNotFoundException("회원정보가 없습니다."));

        return FindUserResponse.createFindUserResponse(user);
    }
}
