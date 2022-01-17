package com.flab.doorrush.global.api;

import com.flab.doorrush.global.dto.request.KakaoApiGetAddressRequest;
import com.flab.doorrush.global.dto.response.AddressDetail;
import com.flab.doorrush.global.dto.response.GetAddressInfo;
import com.flab.doorrush.global.dto.response.KakaoApiGetAddressResponse;
import com.flab.doorrush.global.exception.KakaoApiResponseException;
import java.net.URI;
import java.util.List;
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
public class KakaoAddressApi {

  @Value("${AUTHORIZATION}")
  private String AUTHORIZATION;

  public AddressDetail getAddressBySpot(KakaoApiGetAddressRequest getAddressRequest) {

    String apiUrl = "https://dapi.kakao.com/v2/local/geo/coord2address.json";

    URI url = UriComponentsBuilder.fromHttpUrl(apiUrl)
        .queryParam("x", getAddressRequest.getX())
        .queryParam("y", getAddressRequest.getY())
        .build()
        .toUri();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "KakaoAK " + AUTHORIZATION);
    headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
    headers.add("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8");

    RestTemplate restTemplate = new RestTemplate();
    HttpEntity entity = new HttpEntity<>(headers);
    ResponseEntity<KakaoApiGetAddressResponse> response = restTemplate.exchange(url, HttpMethod.GET,
        entity, KakaoApiGetAddressResponse.class);

    return getAddressDetail(response.getBody());
  }

  public static AddressDetail getAddressDetail(
      KakaoApiGetAddressResponse response) {

    List<GetAddressInfo> list = response.getDocuments();

    if (response.getMeta().get("total_count").equals("0")) {
      throw new KakaoApiResponseException("API 응답결과가 없습니다.");
    }

    String roadAddress = "";
    String buildingName = "";
    String originAddress = "";
    if (!list.get(0).getRoadAddress().isEmpty()) {
      roadAddress = list.get(0).getRoadAddress().get("address_name").toString();
      buildingName = list.get(0).getRoadAddress().get("building_name").toString();
    }
    if (!list.get(0).getAddress().isEmpty()) {
      originAddress = list.get(0).getAddress().get("address_name").toString();
    }
    return new AddressDetail(roadAddress, buildingName, originAddress);
  }
}
