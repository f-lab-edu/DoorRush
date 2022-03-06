package com.flab.doorrush.global.api;

import static com.flab.doorrush.global.api.mock.KakaoMockObject.createKakaoApiFailResponse;
import static com.flab.doorrush.global.api.mock.KakaoMockObject.createKakaoApiRequest;
import static com.flab.doorrush.global.api.mock.KakaoMockObject.createKakaoApiSuccessResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.doorrush.global.dto.response.kakao.KakaoApiGetAddressResponse;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreaker.State;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test-api")
@SpringBootTest
@AutoConfigureWireMock(port = 0)
class KakaoAddressApiTest {


  @Autowired
  KakaoAddressApi kakaoAddressApi;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  CircuitBreakerRegistry CircuitBreakerRegistry;

  @Test
  @DisplayName("좌표로 주소 받아오는 API 성공 테스트")
  public void getAddressSuccessTest() throws JsonProcessingException {

    // Given
    stubFor(get(urlPathEqualTo("/v2/local/geo/coord2address.json"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBody(objectMapper.writeValueAsString(createKakaoApiSuccessResponse()))));

    // When
    ResponseEntity<KakaoApiGetAddressResponse> response = kakaoAddressApi.getAddressBySpot(
        createKakaoApiRequest());

    // Then
    assertEquals(objectMapper.writeValueAsString(createKakaoApiSuccessResponse()),
        objectMapper.writeValueAsString(response.getBody()));
    assertEquals(HttpStatus.OK, response.getStatusCode().value());
  }

  @Test
  @DisplayName("좌표로 주소 받아오는 API 실패 테스트 - fallback 메소드 작동")
  public void getAddressRunningFallbackTest() throws JsonProcessingException {

    // Given
    stubFor(get(urlPathEqualTo("/v2/local/geo/coord2address.json"))
        .willReturn(aResponse()
            .withStatus(500)
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBody(objectMapper.writeValueAsString(createKakaoApiFailResponse()))));

    // When
    ResponseEntity<KakaoApiGetAddressResponse> response = kakaoAddressApi.getAddressBySpot(
        createKakaoApiRequest());

    // Then
    assertEquals(objectMapper.writeValueAsString(response.getBody()),
        objectMapper.writeValueAsString(createKakaoApiFailResponse()));
    assertEquals(HttpStatus.OK, response.getStatusCode().value());
  }

  @Test
  @DisplayName("minimumNumberOfCalls에 도달 시 CircuitBreaker OPEN 상태로 변경")
  public void shouldCircuitBreakerOpenStatus() throws JsonProcessingException {
    // Given
    Optional<CircuitBreaker> circuitBreaker = CircuitBreakerRegistry.find(
        "kakaoAddressApiCircuitBreaker");
    int minimumNumberOfCalls = circuitBreaker.get().getCircuitBreakerConfig()
        .getMinimumNumberOfCalls();

    stubFor(get(urlPathEqualTo("/v2/local/geo/coord2address.json"))
        .willReturn(aResponse()
            .withStatus(500)
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBody(objectMapper.writeValueAsString(createKakaoApiFailResponse()))));

    circuitBreaker.get().reset();
    // When
    for (int i = 1; i < minimumNumberOfCalls + 10; i++) {
      kakaoAddressApi.getAddressBySpot(createKakaoApiRequest());
      // Then
      if (i < minimumNumberOfCalls) {
        assertEquals(State.CLOSED, circuitBreaker.get().getState());
      } else {
        assertEquals(State.OPEN, circuitBreaker.get().getState());
      }
    }
  }
}
