package com.flab.doorrush.domain.order.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderMenusCartResponse {
  private Long totalPrice;
  private List<OrderMenuPrice> orderMenuCarts;
}
