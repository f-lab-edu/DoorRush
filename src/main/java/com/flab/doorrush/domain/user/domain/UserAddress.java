package com.flab.doorrush.domain.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAddress {

  private Long userAddressSeq;
  private Long addressSeq;
  private Long userSeq;
  private String post;
  private Double spotY;
  private Double spotX;
  private String addressDetail;
  private YnStatus defaultYn;

}
