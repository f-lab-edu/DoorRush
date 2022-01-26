package com.flab.doorrush.global.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.flab.doorrush.global.dto.request.KakaoApiGetAddressRequest;
import com.flab.doorrush.global.dto.response.kakao.KakaoApiGetAddressResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;


@SpringBootTest
class KakaoAddressApiTest {

  @Autowired
  KakaoAddressApi kakaoAddressApi;

  @Test
  @DisplayName("좌표로 주소 받아오는 API 성공 테스트")
  public void getAddressSuccessTest() {
    ResponseEntity<KakaoApiGetAddressResponse> result = kakaoAddressApi.getAddressBySpot(KakaoApiGetAddressRequest.builder()
        .x("127.423084873712")
        .y("37.0789561558879")
        .build());

    KakaoApiGetAddressResponse response = result.getBody();
    assertThat(response.getMainAddress().getRoadAddress().getAddressName()).isEqualTo("경기도 안성시 죽산면 죽산초교길 69-4");
    assertThat(response.getMainAddress().getAddress().getAddressName()).isEqualTo("경기 안성시 죽산면 죽산리 343-1");
    assertTrue(response.getMeta().isExist());
  }

}
