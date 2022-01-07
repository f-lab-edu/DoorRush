package com.flab.doorrush.domain.user.dto.response;

import com.flab.doorrush.domain.user.domain.DefaultStatus;
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
  private DefaultStatus defaultStatus;
  private List<UserAddressResponse> userAddresses;

  public static UserAddressResponse from(UserAddress userAddress) {
    return UserAddressResponse.builder()
        .userSeq(userAddress.getUserSeq())
        .addressSeq(userAddress.getAddressSeq())
        .post(userAddress.getPost())
        .spotX(userAddress.getSpotX())
        .spotY(userAddress.getSpotY())
        .addressDetail(userAddress.getAddressDetail())
        .defaultStatus(userAddress.getDefaultStatus())
        .build();
  }

  public static List<UserAddressResponse> toUserAddressResponse(List<UserAddress> userAddress) {
    return userAddress.stream().map(UserAddressResponse::from).collect(Collectors.toList());
  }

  public static boolean isDefault(DefaultStatus defaultStatus) {
    return defaultStatus.getDefaultValue();
  }

}
