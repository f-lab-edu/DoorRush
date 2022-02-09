package com.flab.doorrush.global.dto.response.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAddressInfo {

  @JsonProperty("road_address")
  private RoadAddress roadAddress;

  private Address address;

}
