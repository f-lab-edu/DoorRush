package com.flab.doorrush.domain.order.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Order {

  private Long orderSeq;
  private Long userSeq;
  private String address;
  private Long restaurantSeq;
  private String restaurantName;
  private OrderStatus orderStatus;
  private Long amount;
  private String orderTime;

}
