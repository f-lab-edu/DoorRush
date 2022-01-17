package com.flab.doorrush.global.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoApiGetAddressRequest {
  private String x;
  private String y;
}
