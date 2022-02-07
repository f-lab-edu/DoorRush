package com.flab.doorrush.domain.user.service;


import com.flab.doorrush.domain.user.dao.UserAddressMapper;
import com.flab.doorrush.domain.user.domain.Address;
import com.flab.doorrush.domain.user.domain.UserAddress;
import com.flab.doorrush.domain.user.dto.request.UserAddressRequest;
import com.flab.doorrush.domain.user.dto.response.AddressInfoDTO;
import com.flab.doorrush.domain.user.dto.response.UserAddressResponse;
import com.flab.doorrush.domain.user.exception.NotExistsAddressException;
import com.flab.doorrush.global.api.KakaoAddressApi;
import com.flab.doorrush.global.dto.request.KakaoApiGetAddressRequest;
import com.flab.doorrush.global.dto.response.kakao.KakaoApiGetAddressResponse;
import com.flab.doorrush.global.exception.KakaoApiResponseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAddressService {

  private final UserAddressMapper userAddressMapper;
  private final KakaoAddressApi kakaoAddressApi;

  public List<UserAddressResponse> getUserAddress(Long userSeq) {
    return UserAddressResponse.fromUserAddressResponse(
        userAddressMapper.selectUserAddressAll(userSeq));
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
        .defaultYn(userAddressRequest.getDefaultYn()).build();

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
        && userAddressRequest.isDefault(userAddressRequest.getDefaultYn());
  }

  public AddressInfoDTO getOriginAddress(Long addressSeq) {
    Address address = userAddressMapper.selectAddressBySeq(addressSeq)
        .orElseThrow(() -> new NotExistsAddressException("주소정보가 잘못되었습니다."));

    KakaoApiGetAddressRequest request = KakaoApiGetAddressRequest.builder()
        .x(address.getSpotX().toString())
        .y(address.getSpotY().toString())
        .build();
    return parseKakaoApiGetAddressResponse(kakaoAddressApi.getAddressBySpot(request).getBody());
  }

  private AddressInfoDTO parseKakaoApiGetAddressResponse(
      KakaoApiGetAddressResponse response) {

    if (!response.getMeta().isExist()) {
      throw new KakaoApiResponseException("API 응답결과가 없습니다.");
    }

    String roadAddress = "";
    String buildingName = "";
    String originAddress = "";
    if (!response.getMainAddress().getRoadAddress().isExist()) {
      roadAddress = response.getMainAddress().getRoadAddress().getAddressName();
      buildingName = response.getMainAddress().getRoadAddress().getBuildingName();
    }
    if (!response.getMainAddress().getAddress().isExist()) {
      originAddress = response.getMainAddress().getAddress().getAddressName();
    }
    return AddressInfoDTO.builder()
        .roadAddress(roadAddress)
        .originAddress(originAddress)
        .buildingName(buildingName)
        .build();
  }
}
