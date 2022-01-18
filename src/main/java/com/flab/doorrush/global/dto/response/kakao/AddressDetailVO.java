package com.flab.doorrush.global.dto.response.kakao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDetailVO {

  private String roadAddress;
  private String buildingName;
  private String originAddress;
}
