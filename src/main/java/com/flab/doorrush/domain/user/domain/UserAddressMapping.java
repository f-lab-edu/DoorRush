package com.flab.doorrush.domain.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAddressMapping {

  private Long userAddressSeq;
  private Long userSeq;
  private Long addressSeq;
  private String defaultYn;

}
