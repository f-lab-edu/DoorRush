package com.flab.doorrush.global.dto.response;

import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class KakaoApiGetAddressResponse{

  private Map<String,String> meta ;
  private List<GetAddressInfo> documents;
}
