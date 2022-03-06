package com.flab.doorrush.domain.restaurant.restaurantEnum;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestaurantCategoryTest {

  @Test
  public void confirmRestaurantCategoryTest() {

    assertEquals("양식", RestaurantCategory.WESTERN.categoryValue);
    assertEquals("중식", RestaurantCategory.CHINESE.categoryValue);
    assertEquals("일식", RestaurantCategory.JAPANESE.categoryValue);
    assertEquals("동남아", RestaurantCategory.SOUTHEAST_ASIAN.categoryValue);
    assertEquals("분식", RestaurantCategory.SNACK.categoryValue);

  }
}