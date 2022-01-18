package com.flab.doorrush.global.dto.response.kakao;

import java.util.List;
import lombok.Getter;

@Getter
public class KakaoApiGetAddressResponse {

  private MetaVO meta;
  private List<GetAddressInfoVO> documents;

  public GetAddressInfoVO getFirstIndex() {
    return documents.get(0);
  }

}
