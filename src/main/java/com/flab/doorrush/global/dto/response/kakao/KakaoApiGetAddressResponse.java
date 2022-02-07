package com.flab.doorrush.global.dto.response.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flab.doorrush.global.exception.KakaoApiResponseException;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties({"mainAddress"})
public class KakaoApiGetAddressResponse {

  private Meta meta;
  private List<GetAddressInfo> documents;

  public GetAddressInfo getMainAddress() {
    if (documents.isEmpty()) {
      throw new KakaoApiResponseException("응답값이 없습니다.");
    }
    return documents.get(0);
  }

}
