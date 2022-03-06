package com.flab.doorrush.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.flab.doorrush.domain.user.dao.UserAddressMapper;
import com.flab.doorrush.domain.user.domain.Address;
import com.flab.doorrush.domain.user.domain.UserAddress;
import com.flab.doorrush.domain.user.domain.YnStatus;
import com.flab.doorrush.domain.user.dto.request.UserAddressRequest;
import com.flab.doorrush.domain.user.dto.response.AddressInfoDTO;
import com.flab.doorrush.domain.user.dto.response.UserAddressResponse;
import com.flab.doorrush.domain.user.exception.NotExistsAddressException;
import com.flab.doorrush.global.api.KakaoAddressApi;
import com.flab.doorrush.global.api.mock.KakaoMockObject;
import com.flab.doorrush.global.dto.response.kakao.KakaoApiGetAddressResponse;
import com.flab.doorrush.global.exception.KakaoApiResponseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserAddressServiceTest {

  @Mock
  UserAddressMapper userAddressMapper;

  @Mock
  KakaoAddressApi kakaoAddressApi;

  @InjectMocks
  UserAddressService userAddressService;

  Address address;
  UserAddress userAddress1;
  UserAddress userAddress2;
  UserAddress userAddress3;
  UserAddressRequest userAddressRequestWithDefaultY;
  UserAddressRequest userAddressRequestWithDefaultN;

  @BeforeEach
  void setup() {
    address = Address.builder()
        .addressSeq(1L)
        .post("13561")
        .spotY(37.35951219616309)
        .spotX(127.10522081658463)
        .addressDetail("펜트하우스")
        .build();

    userAddress1 = UserAddress.builder()
        .userAddressSeq(1L)
        .post("13561")
        .spotY(37.35951219616309)
        .spotX(127.10522081658463)
        .addressDetail("301호")
        .defaultYn(YnStatus.Y)
        .build();

    userAddress2 = UserAddress.builder()
        .userAddressSeq(2L)
        .post("13561")
        .spotY(37.35951219616309)
        .spotX(127.10522081658463)
        .addressDetail("1208호")
        .defaultYn(YnStatus.N)
        .build();

    userAddress3 = UserAddress.builder()
        .userAddressSeq(2L)
        .post("13561")
        .spotX(123.334433)
        .spotY(24.5553443)
        .addressDetail("d동 1301호")
        .defaultYn(YnStatus.N)
        .build();

    userAddressRequestWithDefaultY = UserAddressRequest.builder()
        .post("12345")
        .spotX(123.334433)
        .spotY(24.5553443)
        .addressDetail("꼭대기층 1230호")
        .defaultYn(YnStatus.Y)
        .build();

    userAddressRequestWithDefaultN = UserAddressRequest.builder()
        .post("54321")
        .spotX(152.157482231)
        .spotY(33.5486454853)
        .addressDetail("B동 201호")
        .defaultYn(YnStatus.N)
        .build();
  }

  @Test
  @DisplayName("회원의 배달지 주소 조회 성공 테스트(맵핑된 주소가 있는 경우)")

  public void getUserAddressSuccessTest() {
    // Given
    Long userSeq = 25L;
    given(userAddressMapper.selectUserAddressAll(any()))
        .willReturn(Arrays.asList(userAddress1, userAddress2));

    // When
    List<UserAddressResponse> list = userAddressService.getUserAddress(userSeq);

    // Then
    assertFalse(list.isEmpty());
    assertEquals(list.size(), 2);
  }

  @Test
  @DisplayName("회원의 배달지 주소 조회 성공 테스트(맵핑된 주소가 없는 경우)")
  public void getUserAddressEmptyTest() {
    // Given
    Long userSeq = 25L;
    given(userAddressMapper.selectUserAddressAll(any())).willReturn(List.of());

    // When
    List<UserAddressResponse> list = userAddressService.getUserAddress(userSeq);

    // Then
    assertTrue(list.isEmpty());
  }

  @Test
  @DisplayName("회원 배달지 주소 등록 성공 테스트 - 기본배달지로 설정 (update도 진행)")
  public void registerDefaultAddressSuccessTestWithUpdate() {
    // Given
    Long userSeq = 25L;
    given(userAddressMapper.isExistsDefaultAddress(userSeq)).willReturn(true);
    given(userAddressMapper.selectUserAddressAll(any()))
        .willReturn(Arrays.asList(userAddress1, userAddress2));

    // When
    userAddressService.registerAddress(userSeq, userAddressRequestWithDefaultY);

    // Then
    List<UserAddressResponse> list = userAddressService.getUserAddress(userSeq);
    assertEquals(
        list.stream().filter(address -> address.isDefault(address.getDefaultYn()))
            .count(), 1);
    assertFalse(list.isEmpty());
    verify(userAddressMapper, times(1)).updateUserAddress(userSeq);
    verify(userAddressMapper, times(1)).insertAddress(any());
    verify(userAddressMapper, times(1)).insertUserAddress(any());
  }

  @Test
  @DisplayName("회원 배달지 주소 등록 성공 테스트 - 기본배달지로 설정 (update 미진행)")
  public void registerDefaultAddressSuccessTestWithoutUpdate() {
    // Given
    Long userSeq = 25L;
    given(userAddressMapper.isExistsDefaultAddress(userSeq)).willReturn(false);
    given(userAddressMapper.selectUserAddressAll(any()))
        .willReturn(Arrays.asList(userAddress1, userAddress2));

    // When
    userAddressService.registerAddress(userSeq, userAddressRequestWithDefaultN);

    // Then
    List<UserAddressResponse> list = userAddressService.getUserAddress(userSeq);
    assertEquals(
        list.stream().filter(address -> address.isDefault(address.getDefaultYn()))
            .count(), 1);
    assertFalse(list.isEmpty());
    verify(userAddressMapper, never()).updateUserAddress(userSeq);
    verify(userAddressMapper, times(1)).insertAddress(any());
    verify(userAddressMapper, times(1)).insertUserAddress(any());
  }

  @Test
  @DisplayName("회원 배달지 주소 등록 성공 테스트 - 기본배달지로 미설정")
  public void registerAddressSuccessTest() {
    // Given
    Long userSeq = 25L;
    given(userAddressMapper.isExistsDefaultAddress(userSeq)).willReturn(false);
    given(userAddressMapper.selectUserAddressAll(any()))
        .willReturn(Arrays.asList(userAddress1, userAddress2));
    given(userAddressMapper.selectUserAddressAll(userSeq))
        .willReturn(Arrays.asList(userAddress1, userAddress2, userAddress3));

    // When
    userAddressService.registerAddress(userSeq, userAddressRequestWithDefaultN);

    // Then
    verify(userAddressMapper, never()).updateUserAddress(userSeq);
    verify(userAddressMapper, times(1)).insertAddress(any());
    verify(userAddressMapper, times(1)).insertUserAddress(any());
    List<UserAddressResponse> list = userAddressService.getUserAddress(userSeq);
    assertEquals(list.stream()
        .filter(address -> address.isDefault(address.getDefaultYn())).count(), 1);
    assertFalse(list.isEmpty());
    assertEquals(list.size(), 3);
  }

  @Test
  @DisplayName("회원 배달지 삭제 성공 테스트")
  public void deleteAddressSuccessTest() {
    // Given
    given(userAddressMapper.isExistsAddress(any())).willReturn(true);
    given(userAddressMapper.deleteAddress(any())).willReturn(1);

    // When
    boolean isDelete = userAddressService.deleteAddress(1L);

    //Then
    assertTrue(isDelete);
  }

  @Test
  @DisplayName("회원 배달지 삭제 실패 테스트")
  public void deleteAddressFailTest() {

    // Given
    given(userAddressMapper.isExistsAddress(any())).willReturn(false);

    // Then
    assertThrows(NotExistsAddressException.class,
        // When
        () -> userAddressService.deleteAddress(1L));
  }

  @Test
  @DisplayName("좌표로 주소 반환 성공 테스트")
  public void getOriginAddressSuccessTest() {
    // Given
    KakaoApiGetAddressResponse response = KakaoMockObject.createKakaoApiSuccessResponse();
    given(userAddressMapper.selectAddressBySeq(any())).willReturn(Optional.of(address));
    given(kakaoAddressApi.getAddressBySpot(any()))
        .willReturn(ResponseEntity.ok(response));

    // When
    AddressInfoDTO addressInfoDTO = userAddressService.getOriginAddress(1L);

    // Then
    verify(kakaoAddressApi, times(1)).getAddressBySpot(any());
    assertTrue(response.getMeta().isExist());
    assertEquals(response.getMainAddress().getAddress().getAddressName(),
        addressInfoDTO.getOriginAddress());
  }

  @Test
  @DisplayName("좌표로 주소 반환 테스트 - API 반환값 미존재 NotExistsAddressException 에러 발생")
  public void getOriginAddressApiFailTest() {
    // Given
    KakaoApiGetAddressResponse response = KakaoMockObject.createKakaoApiSuccessResponse();
    given(userAddressMapper.selectAddressBySeq(any())).willReturn(Optional.empty());

    // Then
    assertThrows(NotExistsAddressException.class,
        // When
        () -> userAddressService.getOriginAddress(1L));
  }

  @Test
  @DisplayName("좌표로 주소 반환 테스트 - 주소정보 미존재 KakaoApiResponseException 에러 발생")
  public void getOriginAddressFailTest() {
    // Given
    KakaoApiGetAddressResponse response = KakaoMockObject.createKakaoApiFailResponse();
    given(userAddressMapper.selectAddressBySeq(any())).willReturn(Optional.of(address));
    given(kakaoAddressApi.getAddressBySpot(any()))
        .willReturn(ResponseEntity.ok(response));

    // Then
    assertThrows(KakaoApiResponseException.class,
        // When
        () -> userAddressService.getOriginAddress(1L));
  }


}
