package com.flab.doorrush.global.dto.response.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AddressVO {

  @JsonProperty("address_name")
  private String addressName;

  @JsonProperty("region_1depth_name")
  private String region1DepthName;

  @JsonProperty("region_2depth_name")
  private String region2DepthName;

  @JsonProperty("region_3depth_name")
  private String region3DepthName;

  @JsonProperty("mountain_yn")
  private String mountainYn;

  @JsonProperty("main_address_no")
  private String mainAddressNo;

  @JsonProperty("sub_address_no")
  private String subAddressNo;

  @JsonProperty("zip_code")
  private String zipCode;

  public boolean isExist() {
    return addressName.isEmpty();
  }
}
