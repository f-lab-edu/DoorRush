package com.flab.doorrush.domain.user.dto.response;

import com.flab.doorrush.domain.user.domain.Address;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAddressResponse {

  Address address;
  private List<Address> addressList;

  public static UserAddressResponse from(Address address) {
    return UserAddressResponse.builder()
        .address(address)
        .build();
  }
}
