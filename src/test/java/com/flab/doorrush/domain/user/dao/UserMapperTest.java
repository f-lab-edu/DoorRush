package com.flab.doorrush.domain.user.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserMapperTest {

  @Autowired
  UserMapper userMapper;

  @Test
  @DisplayName("사용자 insert 테스트 userSeq 값이 반환된다.")
  public void userInsertTest() {

    // Given
    String id = "testID1234";
    String password = "testPW";
    String name = "yeonjae";
    String phoneNumber = "0102222333";
    String email = "yj@naver.com";

    User user = User.builder()
        .loginId(id)
        .password(password)
        .phoneNumber(phoneNumber)
        .email(email)
        .name(name)
        .build();

    // When
    userMapper.insertUser(user);

    // Then
    assertThat(user.getUserSeq()).isNotNull();
  }

  @Test
  @DisplayName("아이디로 사용자 조회 테스트")
  public void selectUserByIdTest() {
    // Given
    String id = "test1";

    // When
    Optional<User> user = userMapper.selectUserById(id);

    // Then
    if (user.isPresent()) {
      assertThat(user.get().getName()).isEqualTo("11");
    } else {
      throw new UserNotFoundException("회원정보가 없습니다.");
    }
  }

}