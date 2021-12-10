package com.flab.doorrush.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
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
