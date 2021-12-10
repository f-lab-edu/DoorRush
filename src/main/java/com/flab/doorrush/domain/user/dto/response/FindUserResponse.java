package com.flab.doorrush.domain.user.dto.response;

import com.flab.doorrush.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindUserResponse {

    private User user;

    public FindUserResponse createFindUserResponse(User user) {
        return new FindUserResponse(user);
    }
}
