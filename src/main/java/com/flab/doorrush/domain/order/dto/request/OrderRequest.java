package com.flab.doorrush.domain.order.dto.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderRequest {

  @NotNull
  private List<Menu> menus;
  @NotNull
  private Long restaurantSeq;
  @NotNull
  private Long addressSeq;
  @NotNull
  private Long amount;
}
