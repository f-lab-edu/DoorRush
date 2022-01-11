package com.flab.doorrush.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BasicResponse<T> {

  private boolean success;
  private T data;

  public BasicResponse(T data) {
    this.success = true;
    this.data = data;
  }
}
