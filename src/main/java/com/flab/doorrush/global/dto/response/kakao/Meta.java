package com.flab.doorrush.global.dto.response.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta {

  @JsonProperty("total_count")
  private String totalCount;

  public boolean isExist() {
    return !totalCount.equals("0");
  }
}
