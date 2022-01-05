package com.flab.doorrush.domain.user.dto.request;

import com.flab.doorrush.domain.user.domain.Address;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class UserAddressRequest {

  @NotNull
  private String post;
  @NotNull
  private Double spotY;
  @NotNull
  private Double spotX;
  @NotNull
  private String addressDetail;
  @NotNull
  private String defaultYn;

  public Address toEntity(Long userSeq) {
    return Address.builder()
        .userSeq(userSeq)
        .post(this.getPost())
        .spotY(this.getSpotY())
        .spotX(this.getSpotX())
        .addressDetail(this.getAddressDetail())
        .defaultYn(this.getDefaultYn())
        .build();
  }
}
