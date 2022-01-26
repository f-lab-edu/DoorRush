package com.flab.doorrush.domain.restaurant.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.doorrush.domain.restaurant.domain.Restaurant;
import com.flab.doorrush.domain.restaurant.restaurantEnum.RestaurantCategory;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class AddRestaurantRequest {

  @NotNull
  private RestaurantAddressRequest restaurantAddressRequest;

  private Long ownerSeq;

  @NotNull
  private String category;

  private char openYN;

  @NotNull
  private String restaurantName;

  @NotNull
  private String introduction;

  private Long minimumOrderAmount;


  public Restaurant toEntity(Long addressSeq) {
    return Restaurant.builder()
        .ownerSeq(this.ownerSeq)
        .category(this.category)
        .openYN('N')
        .restaurantName(this.restaurantName)
        .introduction(this.introduction)
        .minimumOrderAmount(0L)
        .addressSeq(addressSeq)
        .build();
  }
}
