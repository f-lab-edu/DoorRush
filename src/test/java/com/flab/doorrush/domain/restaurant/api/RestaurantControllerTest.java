package com.flab.doorrush.domain.restaurant.api;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.doorrush.domain.restaurant.dto.request.AddRestaurantRequest;
import com.flab.doorrush.domain.restaurant.dto.request.RestaurantAddressRequest;
import com.flab.doorrush.domain.restaurant.exception.AddRestaurantException;
import com.flab.doorrush.domain.restaurant.restaurantEnum.RestaurantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RestaurantControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  @DisplayName("addRestaurant 성공 테스트 상태값 200을 반환한다.")
  public void addRestaurantSuccessTest() throws Exception {
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

    String content = objectMapper.writeValueAsString(addRestaurantRequest);

    // When
    mockMvc.perform(post("/restaurants/1/addRestaurant").content(content)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("addRestaurant 실패 테스트 기존에 저장된 address 정보로 요청 시 AddRestaurantException 발생, HttpStatus.BAD_REQUEST 를 반환한다.")
  public void addRestaurantFailTest() throws Exception {
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

    String content = objectMapper.writeValueAsString(addRestaurantRequest);

    // When
    mockMvc.perform(post("/restaurants/1/addRestaurant").content(content)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        // Then
        .andExpect((result) -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(
            AddRestaurantException.class)))
        .andExpect(status().isBadRequest());
  }
}