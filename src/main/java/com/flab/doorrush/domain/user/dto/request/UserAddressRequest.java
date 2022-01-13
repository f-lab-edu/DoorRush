package com.flab.doorrush.domain.user.dto.request;

import com.flab.doorrush.domain.user.domain.Address;
import com.flab.doorrush.domain.user.domain.YnStatus;
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
  private YnStatus ynStatus;

  public boolean isDefault(YnStatus ynStatus) {
    return ynStatus.getYnValue();
  }

  public Address toEntity() {
    return Address.builder()
        .post(this.getPost())
        .spotY(this.getSpotY())
        .spotX(this.getSpotX())
        .addressDetail(this.getAddressDetail())
        .build();
  }

}
