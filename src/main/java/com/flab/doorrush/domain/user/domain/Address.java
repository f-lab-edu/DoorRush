package com.flab.doorrush.domain.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Address {

  private Long addressSeq;
  private String post;
  private Double spotY;
  private Double spotX;
  private String addressDetail;

}
