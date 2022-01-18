package com.flab.doorrush.global.dto.response.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GetAddressInfoVO {

  @JsonProperty("road_address")
  private RoadAddressVO roadAddress;

  private AddressVO address;

}
