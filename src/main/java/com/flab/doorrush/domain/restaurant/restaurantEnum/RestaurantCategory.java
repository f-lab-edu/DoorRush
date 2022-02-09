package com.flab.doorrush.domain.restaurant.restaurantEnum;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum RestaurantCategory {

  WESTERN("양식"),
  CHINESE("중식"),
  JAPANESE("일식"),
  SOUTHEAST_ASIAN("동남아"),
  SNACK("분식");

  public final String category;

  RestaurantCategory(String category) {
    this.category = category;
  }

  @JsonCreator
  public static RestaurantCategory from(@JsonProperty("category") String category) {
    return valueOf(category);
  }


}
