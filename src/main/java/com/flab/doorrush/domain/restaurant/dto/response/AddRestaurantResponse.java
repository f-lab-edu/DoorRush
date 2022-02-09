package com.flab.doorrush.domain.restaurant.dto.response;

import com.flab.doorrush.domain.restaurant.domain.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AddRestaurantResponse {

  private Restaurant restaurant;

  public static AddRestaurantResponse from(Restaurant restaurant) {

    return AddRestaurantResponse.builder()
        .restaurant(restaurant)
        .build();
  }
}
