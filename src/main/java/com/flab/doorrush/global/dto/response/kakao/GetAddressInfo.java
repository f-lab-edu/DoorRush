package com.flab.doorrush.global.dto.response.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GetAddressInfo {

  @JsonProperty("road_address")
  private RoadAddress roadAddress;

  private Address address;

}
