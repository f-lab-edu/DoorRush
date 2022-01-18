package com.flab.doorrush.global.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.flab.doorrush.global.dto.request.KakaoApiGetAddressRequest;
import com.flab.doorrush.global.dto.response.kakao.AddressDetailVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class KakaoAddressApiTest {

  @Autowired
  KakaoAddressApi kakaoAddressApi;

  @Test
  @DisplayName("좌표로 주소 받아오는 API 성공 테스트")
  public void getAddressSuccessTest() {
    AddressDetailVO result = kakaoAddressApi.getAddressBySpot(KakaoApiGetAddressRequest.builder()
        .x("127.423084873712")
        .y("37.0789561558879")
        .build());

    assertThat(result).isNotNull();
    assertThat(result.getRoadAddress()).isEqualTo("경기도 안성시 죽산면 죽산초교길 69-4");
    assertThat(result.getOriginAddress()).isEqualTo("경기 안성시 죽산면 죽산리 343-1");
  }

  @Test
  @DisplayName("좌표로 주소 받아오는 API 실패 테스트")
  public void getAddressFailTest() {
    KakaoApiGetAddressRequest request = KakaoApiGetAddressRequest.builder()
        .x("100.443084873712")
        .y("37.07890561558059")
        .build();

    assertThrows(com.flab.doorrush.global.exception.KakaoApiResponseException.class,
        () -> kakaoAddressApi.getAddressBySpot(request));
  }
}
