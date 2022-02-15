package com.flab.doorrush.domain.order.dto.response;

import com.flab.doorrush.domain.order.domain.OrderStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderHistory {

  private Long orderSeq;
  private String address;
  private Long restaurantSeq;
  private String restaurantName;
  private Long amount;
  private OrderStatus orderStatus;
  private String menuName;
  private Long menuPrice;
  private int menuCount;
  private LocalDateTime orderTime;

}