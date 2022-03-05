package com.flab.doorrush.domain.restaurant.service;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.flab.doorrush.domain.restaurant.dto.request.AddRestaurantRequest;
import com.flab.doorrush.domain.restaurant.dto.request.RestaurantAddressRequest;
import com.flab.doorrush.domain.restaurant.dto.response.AddRestaurantResponse;
import com.flab.doorrush.domain.restaurant.exception.AddRestaurantException;
import com.flab.doorrush.domain.restaurant.restaurantEnum.RestaurantCategory;
import com.flab.doorrush.domain.user.domain.YnStatus;
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

  @Test
  @DisplayName("addRestaurant 성공 테스트 식당 정보 insert 결과 확인")
  public void addRestaurantSuccessTest() {
    // Given
    RestaurantAddressRequest restaurantAddressRequest = RestaurantAddressRequest.builder()
        .post("12345")
        .spotY(37.3595121962)
        .spotX(127.1052208166)
        .addressDetail("30111호").build();

    AddRestaurantRequest addRestaurantRequest = AddRestaurantRequest.builder()
        .restaurantAddressRequest(restaurantAddressRequest)
        .category(RestaurantCategory.CHINESE)
        .openYn(YnStatus.N)
        .restaurantName("맛맛집")
        .introduction("아주 맛있습니다")
        .minimumOrderAmount(0L).build();

    Long ownerSeq = 1L;
    // When
    AddRestaurantResponse restaurantResponse = restaurantService.addRestaurant(addRestaurantRequest,
        ownerSeq);

    // Then
    assertEquals(1L, restaurantResponse.getRestaurant().getOwnerSeq());
    assertEquals(RestaurantCategory.CHINESE.categoryValue,
        restaurantResponse.getRestaurant().getCategory());
    assertEquals(YnStatus.N, restaurantResponse.getRestaurant().getOpenYn());
    assertEquals("맛맛집", restaurantResponse.getRestaurant().getRestaurantName());
    assertEquals("아주 맛있습니다", restaurantResponse.getRestaurant().getIntroduction());

  }

  @Test
  @DisplayName("addRestaurant 실패 테스트 존재하지 않는 사장님 정보로 식당 insert 시 AddRestaurantException 발생")
  public void addRestaurantFailTest() {
    // Given
    RestaurantAddressRequest restaurantAddressRequest = RestaurantAddressRequest.builder()
        .post("13561")
        .spotY(37.3595121962)
        .spotX(127.1052208166)
        .addressDetail("301호").build();

    AddRestaurantRequest addRestaurantRequest = AddRestaurantRequest.builder()
        .restaurantAddressRequest(restaurantAddressRequest)
        .category(RestaurantCategory.CHINESE)
        .openYn(YnStatus.N)
        .restaurantName("맛맛집")
        .introduction("아주 맛있습니다")
        .minimumOrderAmount(0L).build();
    Long ownerSeq = 0L;

    // Then
    assertThrows(AddRestaurantException.class, () ->
        // When
        restaurantService.addRestaurant(addRestaurantRequest, ownerSeq));
  }
}