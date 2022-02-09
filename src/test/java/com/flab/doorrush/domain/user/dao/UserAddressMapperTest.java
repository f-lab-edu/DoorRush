package com.flab.doorrush.domain.user.dao;

import static org.junit.jupiter.api.Assertions.*;

import com.flab.doorrush.domain.restaurant.dto.request.RestaurantAddressRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserAddressMapperTest {

  @Autowired
  UserAddressMapper userAddressMapper;

  @Test
  @DisplayName("selectAddressSeq 테스트 select 결과 addressSeq 값을 반환한다.")
  void selectAddressSeq() {
    // Given
    RestaurantAddressRequest restaurantAddressRequest = RestaurantAddressRequest.builder()
        .post("13561")
        .spotY(37.3595121962).spotX(127.1052208166).addressDetail("301호").build();

    // Then                  When
    assertEquals(1L, userAddressMapper.selectAddressSeq(restaurantAddressRequest.toEntity()));
  }
}