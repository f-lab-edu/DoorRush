package com.flab.doorrush.global.dto.response.kakao;

import java.util.List;
import lombok.Getter;

@Getter
public class KakaoApiGetAddressResponse {

  private Meta meta;
  private List<GetAddressInfo> documents;

  public GetAddressInfo getFirstIndex() {
    return documents.get(0);
  }

}
