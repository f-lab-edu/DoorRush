package com.flab.doorrush.domain.user.domain;

import com.flab.doorrush.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @Data : @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode 의 기능 제공하는 어노테이션
 */

@Data
@Builder
@AllArgsConstructor
public class User {

    private String id;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;

    public UserDto toUserDto(User user) {
        return UserDto.builder()
            .id(user.getId())
            .password(user.getPassword())
            .name(user.getName())
            .email(user.getEmail())
            .phoneNumber(user.getPhoneNumber())
            .build();
    }

}
