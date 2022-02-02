package com.flab.doorrush.domain.restaurant.domain;

import com.flab.doorrush.domain.user.domain.YnStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Restaurant {
  private Long restaurantSeq;
  private Long ownerSeq;
  private String category;
  private YnStatus openYn;
  private String restaurantName;
  private String introduction;
  private Long minimumOrderAmount;
  private Long addressSeq;
}
