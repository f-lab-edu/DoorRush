package com.flab.doorrush.domain.order.dto.response;

import com.flab.doorrush.domain.order.domain.Order;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateOrderResponse {

  private Order order;

  public static CreateOrderResponse from(Order order) {
    return CreateOrderResponse.builder()
        .order(order)
        .build();
  }
}
