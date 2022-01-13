package com.flab.doorrush.domain.user.dto.response;

import com.flab.doorrush.domain.user.domain.YnStatus;
import com.flab.doorrush.domain.user.domain.UserAddress;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAddressResponse {

  private Long userSeq;
  private Long addressSeq;
  private String post;
  private Double spotY;
  private Double spotX;
  private String addressDetail;
  private YnStatus ynStatus;
  private List<UserAddressResponse> userAddresses;

  public static UserAddressResponse from(UserAddress userAddress) {
    return UserAddressResponse.builder()
            .userSeq(userAddress.getUserSeq())
            .addressSeq(userAddress.getAddressSeq())
            .post(userAddress.getPost())
            .spotX(userAddress.getSpotX())
            .spotY(userAddress.getSpotY())
            .addressDetail(userAddress.getAddressDetail())
            .ynStatus(userAddress.getYnStatus())
            .build();
  }

  public static List<UserAddressResponse> fromUserAddressResponse(List<UserAddress> userAddress) {
    return userAddress.stream()
            .map(UserAddressResponse::from)
            .collect(Collectors.toList());
  }

  public boolean isDefault(YnStatus ynStatus) {
    return ynStatus.getYnValue();
  }

}
