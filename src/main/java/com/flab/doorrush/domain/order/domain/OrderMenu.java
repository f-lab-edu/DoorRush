package com.flab.doorrush.domain.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderMenu {

  private Long menuSeq;
  private Long orderSeq;
  private int count;

}