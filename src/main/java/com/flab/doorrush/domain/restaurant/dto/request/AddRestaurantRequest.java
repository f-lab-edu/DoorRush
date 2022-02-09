package com.flab.doorrush.domain.restaurant.dto.request;

import com.flab.doorrush.domain.restaurant.domain.Restaurant;
import com.flab.doorrush.domain.user.domain.YnStatus;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AddRestaurantRequest {

  @NotNull
  private RestaurantAddressRequest restaurantAddressRequest;

  @NotNull
  private String category;

  @NotNull
  private YnStatus openYn;

  @NotNull
  private String restaurantName;

  @NotNull
  private String introduction;

  @NotNull
  private Long minimumOrderAmount;


  public Restaurant toEntity(Long addressSeq, Long ownerSeq) {
    return Restaurant.builder()
        .ownerSeq(ownerSeq)
        .category(this.category)
        .openYn(this.openYn)
        .restaurantName(this.restaurantName)
        .introduction(this.introduction)
        .minimumOrderAmount(this.minimumOrderAmount)
        .addressSeq(addressSeq)
        .build();
  }
}
