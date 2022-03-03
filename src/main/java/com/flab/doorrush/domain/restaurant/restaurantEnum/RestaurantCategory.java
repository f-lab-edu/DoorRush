package com.flab.doorrush.domain.restaurant.restaurantEnum;


public enum RestaurantCategory {

  WESTERN("양식"),
  CHINESE("중식"),
  JAPANESE("일식"),
  SOUTHEAST_ASIAN("동남아"),
  SNACK("분식");

  public final String categoryValue;

  RestaurantCategory(String categoryValue) {
    this.categoryValue = categoryValue;
  }



}
