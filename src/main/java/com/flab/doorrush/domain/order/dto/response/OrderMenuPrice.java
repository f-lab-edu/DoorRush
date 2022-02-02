package com.flab.doorrush.domain.order.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderMenuPrice {

  private String name;
  private Long price;
  private Long menuSumPrice;

}
