package com.flab.doorrush.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddressInfoDTO {
  private String roadAddress;
  private String buildingName;
  private String originAddress;
}
