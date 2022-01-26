package com.flab.doorrush.domain.restaurant.restaurantEnum;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestaurantCategoryTest {

  @Test
  public void confirmRestaurantCategoryTest() {

    assertEquals("양식", RestaurantCategory.WESTERN.category);
    assertEquals("중식", RestaurantCategory.CHINESE.category);
    assertEquals("일식", RestaurantCategory.JAPANESE.category);
    assertEquals("동남아", RestaurantCategory.SOUTHEAST_ASIAN.category);
    assertEquals("분식", RestaurantCategory.SNACK.category);

  }
}