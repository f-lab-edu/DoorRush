package com.flab.doorrush.domain.user.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.flab.doorrush.domain.restaurant.dto.request.RestaurantAddressRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserAddressMapperTest {

  @Autowired
  UserAddressMapper userAddressMapper;


}