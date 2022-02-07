package com.flab.doorrush.global.api;

import com.flab.doorrush.global.dto.request.KakaoApiGetAddressRequest;
import com.flab.doorrush.global.dto.response.kakao.GetAddressInfo;
import com.flab.doorrush.global.dto.response.kakao.KakaoApiGetAddressResponse;
import com.flab.doorrush.global.dto.response.kakao.Meta;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class KakaoAddressApi {

  public static final String KAKAO_HEADER = "KakaoAK ";
  public static final String CUSTOM_CIRCUIT_BREAKER = "kakaoAddressApiCircuitBreaker";

  private final RestTemplate restTemplate;

  @Value("${api.kakao.authorization}")
  private String AUTHORIZATION;

  @Value("${api.kakao.host}")
  public String KAKAO_HOST;

  @Value("${api.kakao.url}")
  public String KAKAO_URL;


  /**
   * @CircuitBreaker resilience4j Spring Boot2 스타터에서 제공되는 어노테이션으로 AOP 측면을 제공하는 역할입니다.
   * CircuitBreaker라는 것을 명시하고 이름과 콜백 메소드를 지정할 수 있습니다.
   */
  @CircuitBreaker(name = CUSTOM_CIRCUIT_BREAKER, fallbackMethod = "fallback")
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

  private ResponseEntity<KakaoApiGetAddressResponse> fallback(
      KakaoApiGetAddressRequest getAddressRequest, Throwable t) {

    log.info("KakaoAddressApi fallback Method running, Exception = {}", t.getMessage());

    List<GetAddressInfo> documents = new ArrayList<>();
    return ResponseEntity.ok().body(KakaoApiGetAddressResponse.builder()
                                      .meta(Meta.builder().totalCount(0).build())
                                      .documents(documents)
                                      .build());
  }

}
