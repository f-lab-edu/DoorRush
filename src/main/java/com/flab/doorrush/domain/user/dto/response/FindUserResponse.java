package com.flab.doorrush.domain.user.dto.response;

import com.flab.doorrush.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FindUserResponse {

    private final User user;

    public static FindUserResponse createFindUserResponse(User user) {
        return new FindUserResponse(user);
    }
}
