package com.flab.doorrush.global.api.mock;

import com.flab.doorrush.global.dto.request.KakaoApiGetAddressRequest;
import com.flab.doorrush.global.dto.response.kakao.Address;
import com.flab.doorrush.global.dto.response.kakao.GetAddressInfo;
import com.flab.doorrush.global.dto.response.kakao.KakaoApiGetAddressResponse;
import com.flab.doorrush.global.dto.response.kakao.Meta;
import com.flab.doorrush.global.dto.response.kakao.RoadAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KakaoMockObject {

  private static final int TOTAL_COUNT = 0;
  private static final int SUCCESS_TOTAL_COUNT = 1;
  private static final String ROAD_ADDRESS_NAME = "경기도 안성시 죽산면 죽산초교길 69-4";
  private static final String ROAD_NAME = "죽산초교길";
  private static final String ROAD_BUILDING_NAME = "무지개아파트";
  private static final String ADDRESS_NAME = "경기 안성시 죽산면 죽산리 343-1";

  public static KakaoApiGetAddressRequest createKakaoApiRequest() {
    return KakaoApiGetAddressRequest.builder()
        .x("127.423084873712")
        .y("37.0789561558879")
        .build();
  }

  public static KakaoApiGetAddressResponse createKakaoApiSuccessResponse() {
    GetAddressInfo getAddressInfo = GetAddressInfo.builder()
        .address(Address.builder()
            .addressName(ADDRESS_NAME)
            .build())
        .roadAddress(RoadAddress.builder()
            .addressName(ROAD_ADDRESS_NAME)
            .roadName(ROAD_NAME)
            .buildingName(ROAD_BUILDING_NAME)
            .build())
        .build();

    List<GetAddressInfo> documents = Arrays.asList(getAddressInfo);

    return KakaoApiGetAddressResponse.builder()
        .meta(Meta.builder()
            .totalCount(SUCCESS_TOTAL_COUNT)
            .build())
        .documents(documents)
        .build();
  }
  public static KakaoApiGetAddressResponse createKakaoApiFailResponse() {
    List<GetAddressInfo> documents = new ArrayList<>();

    return KakaoApiGetAddressResponse.builder()
        .meta(Meta.builder()
            .totalCount(TOTAL_COUNT)
            .build())
        .documents(documents)
        .build();
  }
}
