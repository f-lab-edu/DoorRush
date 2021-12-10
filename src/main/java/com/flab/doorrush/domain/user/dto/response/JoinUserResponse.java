package com.flab.doorrush.domain.user.dto.response;

import com.flab.doorrush.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // getter 메소드를 생성해주는 어노테이션입니다.
@Builder // 디자인 패턴 중 하나인 빌더 패턴 (Builder pattern)를 사용가능하게 해주는 어노테이션입니다.
@AllArgsConstructor
@NoArgsConstructor
public class JoinUserResponse {

    private User user;

    public JoinUserResponse createJoinUserResponse(User user) {
        return new JoinUserResponse(user);
    }

}
