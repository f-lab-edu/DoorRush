package com.flab.doorrush.domain.restaurant.service;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.flab.doorrush.domain.restaurant.dao.RestaurantMapper;
import com.flab.doorrush.domain.restaurant.domain.Restaurant;
import com.flab.doorrush.domain.restaurant.dto.request.AddRestaurantRequest;
import com.flab.doorrush.domain.restaurant.dto.request.RestaurantAddressRequest;
import com.flab.doorrush.domain.restaurant.exception.AddRestaurantException;
import com.flab.doorrush.domain.restaurant.restaurantEnum.RestaurantCategory;
import com.flab.doorrush.domain.user.dao.UserAddressMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RestaurantServiceTest {

  @Autowired
  private RestaurantService restaurantService;

  @Autowired
  private RestaurantMapper restaurantMapper;

  @Autowired
  private UserAddressMapper userAddressMapper;

  @Test
  public void addRestaurantSuccessTest() {
    // Given
    RestaurantAddressRequest restaurantAddressRequest = RestaurantAddressRequest.builder()
        .post("12345")
        .spotY(37.3595121962)
        .spotX(127.1052208166)
        .addressDetail("30111호").build();

    AddRestaurantRequest addRestaurantRequest = AddRestaurantRequest.builder()
        .restaurantAddressRequest(restaurantAddressRequest)
        .ownerSeq(1L)
        .category(RestaurantCategory.CHINESE.category)
        .openYN('N')
        .restaurantName("맛맛집")
        .introduction("아주 맛있습니다")
        .minimumOrderAmount(0L).build();

    // When
    restaurantService.addRestaurant(addRestaurantRequest);

    // Then
    Long addressSeq = userAddressMapper.selectAddressSeq(restaurantAddressRequest.toEntity());
    Long restaurantSeq = restaurantMapper.selectRestaurantSeq(
        addRestaurantRequest.toEntity(addressSeq));
    Restaurant restaurant = restaurantMapper.selectRestaurantByRestaurantSeq(restaurantSeq);
    assertEquals(restaurantSeq, restaurant.getRestaurantSeq());
    assertEquals(1L, restaurant.getOwnerSeq());
    assertEquals(RestaurantCategory.CHINESE.category, restaurant.getCategory());
    assertEquals('N', restaurant.getOpenYN());
    assertEquals("맛맛집", restaurant.getRestaurantName());
    assertEquals("아주 맛있습니다", restaurant.getIntroduction());
    assertEquals(0L, restaurant.getMinimumOrderAmount());
    assertEquals(addressSeq, restaurant.getAddressSeq());
  }

  @Test
  @DisplayName("기존에 저장된 address 정보로 식당 insert 시 AddRestaurantException 발생")
  public void addRestaurantFailTest() {
    // Given
    RestaurantAddressRequest restaurantAddressRequest = RestaurantAddressRequest.builder()
        .post("13561")
        .spotY(37.3595121962)
        .spotX(127.1052208166)
        .addressDetail("301호").build();

    AddRestaurantRequest addRestaurantRequest = AddRestaurantRequest.builder()
        .restaurantAddressRequest(restaurantAddressRequest)
        .ownerSeq(1L)
        .category(RestaurantCategory.CHINESE.category)
        .openYN('N')
        .restaurantName("맛맛집")
        .introduction("아주 맛있습니다")
        .minimumOrderAmount(0L).build();

    // Then
    assertThrows(AddRestaurantException.class, () ->
        // When
        restaurantService.addRestaurant(addRestaurantRequest));
  }
}