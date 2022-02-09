package com.flab.doorrush.domain.restaurant.dto.request;

import com.flab.doorrush.domain.user.domain.Address;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RestaurantAddressRequest {

  @NotNull
  private String post;
  @NotNull
  private Double spotY;
  @NotNull
  private Double spotX;
  @NotNull
  private String addressDetail;


  public Address toEntity() {
    return Address.builder()
        .post(this.getPost())
        .spotY(this.getSpotY())
        .spotX(this.getSpotX())
        .addressDetail(this.getAddressDetail())
        .build();
  }

}
