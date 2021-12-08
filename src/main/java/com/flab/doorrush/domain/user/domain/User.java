package com.flab.doorrush.domain.user.domain;

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

    private Long userSeq;
    private String loginId;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;

}
