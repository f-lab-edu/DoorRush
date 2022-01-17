package com.flab.doorrush.domain.order.dto.request;

import com.flab.doorrush.domain.order.domain.OrderMenu;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MenuDto {
  @NotNull
  private Long menuSeq;
  @NotNull
  private int count;

  public OrderMenu toEntity(Long orderSeq){
    return OrderMenu.builder()
        .menuSeq(this.menuSeq)
        .orderSeq(orderSeq)
        .count(this.count)
        .build();
  }

}
