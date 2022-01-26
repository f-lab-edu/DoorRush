package com.flab.doorrush.global.api;

import com.flab.doorrush.global.dto.request.KakaoApiGetAddressRequest;
import com.flab.doorrush.global.dto.response.kakao.KakaoApiGetAddressResponse;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoAddressApi {

  private final RestTemplate restTemplate;

  @Value("${api.authorization}")
  private String AUTHORIZATION;

  public static final String KAKAO_HEADER = "KakaoAK ";
  public static final String KAKAO_HOST = "https://dapi.kakao.com";
  public static final String KAKAO_URL = "/v2/local/geo/coord2address.json";

  public ResponseEntity<KakaoApiGetAddressResponse> getAddressBySpot(
      KakaoApiGetAddressRequest getAddressRequest) {

    URI url = UriComponentsBuilder.fromHttpUrl(KAKAO_HOST + KAKAO_URL)
        .queryParam("x", getAddressRequest.getX())
        .queryParam("y", getAddressRequest.getY())
        .build()
        .toUri();

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, KAKAO_HEADER + AUTHORIZATION);
    headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    headers.add(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_JSON));
    headers.add(HttpHeaders.ACCEPT_CHARSET, String.valueOf(StandardCharsets.UTF_8));

    HttpEntity entity = new HttpEntity<>(headers);

    return restTemplate.exchange(url, HttpMethod.GET, entity, KakaoApiGetAddressResponse.class);
  }

}
