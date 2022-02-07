package com.flab.doorrush.domain.restaurant.dao;

import static org.junit.jupiter.api.Assertions.*;

import com.flab.doorrush.domain.restaurant.domain.Restaurant;
import com.flab.doorrush.domain.user.domain.YnStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RestaurantMapperTest {

  @Autowired
  RestaurantMapper restaurantMapper;

  @Test
  @DisplayName("insertRestaurant 성공 테스트 int 타입 1 반환 한다.")
  public void insertRestaurantSuccessTest() {
    // Given
    Restaurant restaurant = Restaurant.builder()
        .ownerSeq(5L)
        .category("중식")
        .openYn(YnStatus.Y)
        .restaurantName("냠냠식당")
        .introduction("맛좋습니다.")
        .minimumOrderAmount(0L)
        .addressSeq(5L)
        .build();

    // Then       When
    assertEquals(1, restaurantMapper.insertRestaurant(restaurant));
  }

  @Test
  @DisplayName("selectRestaurantByRestaurantSeq 성공 테스트 select 결과 Restaurant 반환 한다.")
  public void selectRestaurantByRestaurantSeqTest() {
    // Given
    Long restaurantSeq = 1L;

    // Then                 When
    Restaurant restaurant = restaurantMapper.selectRestaurantByRestaurantSeq(restaurantSeq);
    assertEquals(1, restaurant.getRestaurantSeq());
    assertEquals(1, restaurant.getOwnerSeq());
    assertEquals("중식", restaurant.getCategory());
    assertEquals('Y', restaurant.getOpenYn());
    assertEquals("중식중 최고집", restaurant.getRestaurantName());
    assertEquals("증식집 중 최고를 자랑합니다.", restaurant.getIntroduction());
    assertEquals(12000, restaurant.getMinimumOrderAmount());
    assertEquals(5, restaurant.getAddressSeq());
  }


  @Test
  @DisplayName("selectRestaurantSeq 성공 테스트 select 결과 restaurantSeq 값 반환 한다.")
  public void selectRestaurantSeqTest() {
    // Given
    Restaurant restaurant = Restaurant.builder()
        .ownerSeq(1L)
        .category("중식")
        .openYn(YnStatus.Y)
        .restaurantName("중식중 최고집")
        .introduction("증식집 중 최고를 자랑합니다.")
        .minimumOrderAmount(12000L)
        .addressSeq(5L)
        .build();

    // Then              When
    Long restaurantSeq = restaurantMapper.selectRestaurantSeq(restaurant);
    assertEquals(1, restaurantSeq);
  }
}