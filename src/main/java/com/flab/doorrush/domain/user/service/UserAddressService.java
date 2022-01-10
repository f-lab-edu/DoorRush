package com.flab.doorrush.domain.user.service;


import com.flab.doorrush.domain.user.dao.UserAddressMapper;
import com.flab.doorrush.domain.user.domain.Address;
import com.flab.doorrush.domain.user.domain.UserAddress;
import com.flab.doorrush.domain.user.dto.request.UserAddressRequest;
import com.flab.doorrush.domain.user.dto.response.UserAddressResponse;
import com.flab.doorrush.domain.user.exception.NotExistsAddressException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAddressService {

  private final UserAddressMapper userAddressMapper;

  public List<UserAddressResponse> getUserAddress(Long userSeq) {
    return UserAddressResponse.fromUserAddressResponse(userAddressMapper.selectUserAddressAll(userSeq));
  }

  @Transactional
  public UserAddressResponse registerAddress(Long userSeq, UserAddressRequest userAddressRequest) {
    Address address = userAddressRequest.toEntity();
    userAddressMapper.insertAddress(address);

    if (shouldUpdateUserAddress(userSeq, userAddressRequest)) {
      userAddressMapper.updateUserAddress(userSeq);
    }

    UserAddress userAddress = UserAddress.builder()
        .userSeq(userSeq)
        .addressSeq(address.getAddressSeq())
        .post(address.getPost())
        .spotY(address.getSpotY())
        .spotX(address.getSpotX())
        .addressDetail(address.getAddressDetail())
        .ynStatus(userAddressRequest.getYnStatus()).build();

    userAddressMapper.insertUserAddress(userAddress);

    return UserAddressResponse.from(userAddress);
  }

  public boolean deleteAddress(Long addressSeq) {
    if (!userAddressMapper.isExistsAddress(addressSeq)) {
      throw new NotExistsAddressException("존재하지 않는 주소정보입니다.");
    }
    return userAddressMapper.deleteAddress(addressSeq) > 0;
  }

  private boolean shouldUpdateUserAddress(Long userSeq, UserAddressRequest userAddressRequest) {
    return userAddressMapper.isExistsDefaultAddress(userSeq)
        && userAddressRequest.isDefault(userAddressRequest.getYnStatus());
  }

}
