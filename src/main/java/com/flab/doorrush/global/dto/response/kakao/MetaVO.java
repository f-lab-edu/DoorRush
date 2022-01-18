package com.flab.doorrush.global.dto.response.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetaVO {

  @JsonProperty("total_count")
  private String totalCount;

  public boolean isInvalid() {
    return totalCount.equals("0");
  }
}
