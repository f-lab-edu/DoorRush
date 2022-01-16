package com.flab.doorrush.global.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Getter;

@Getter
public class GetAddressInfo {

  @JsonProperty("road_address")
  private Map<String, Object> roadAddress;

  private Map<String, Object> address;

  @JsonProperty("address_name")
  private Map<String, Object> addressName;

  @JsonProperty("address_type")
  private Map<String, Object> addressType;

  private Map<String, Object> x;

  private Map<String, Object> y;
}
