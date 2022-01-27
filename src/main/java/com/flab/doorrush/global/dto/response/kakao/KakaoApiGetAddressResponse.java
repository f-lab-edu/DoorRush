package com.flab.doorrush.global.dto.response.kakao;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoApiGetAddressResponse {

  private Meta meta;
  private List<GetAddressInfo> documents;

  public GetAddressInfo getMainAddress() {
    return documents.get(0);
  }

}
