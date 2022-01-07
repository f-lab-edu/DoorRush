package com.flab.doorrush.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.flab.doorrush.domain.user.domain.DefaultStatus;
import com.flab.doorrush.domain.user.dto.request.UserAddressRequest;
import com.flab.doorrush.domain.user.dto.response.UserAddressResponse;
import com.flab.doorrush.domain.user.exception.NotExistsAddressException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserAddressServiceTest {

  @Autowired
  UserAddressService userAddressService;

  @Test
  @DisplayName("회원의 배달지 주소 조회 성공 테스트(맵핑된 주소가 있는 경우)")
  public void getUserAddressSuccessTest() {
    // Given
    Long userSeq = 25L;

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
    Long userSeq = 1L;

    // When
    List<UserAddressResponse> list = userAddressService.getUserAddress(userSeq);

    // Then
    assertTrue(list.isEmpty());
  }

  @Test
  @DisplayName("회원 배달지 주소 등록 성공 테스트 - 기본배달지로 설정")
  public void registerDefaultAddressSuccessTest() {

    // Given
    Long userSeq = 25L;
    UserAddressRequest userAddressRequest = UserAddressRequest.builder()
        .post("12345")
        .spotX(123.334433)
        .spotY(24.5553443)
        .addressDetail("꼭대기층 1230호")
        .defaultStatus(DefaultStatus.Y)
        .build();

    // When
    userAddressService.registerAddress(userSeq, userAddressRequest);

    // Then
    List<UserAddressResponse> list = userAddressService.getUserAddress(userSeq);
    assertEquals(
        list.stream().filter(address -> UserAddressResponse.isDefault(address.getDefaultStatus()))
            .count(), 1);
    assertFalse(list.isEmpty());
  }


  @Test
  @DisplayName("회원 배달지 주소 등록 성공 테스트 - 기본배달지로 미설정")
  public void registerAddressSuccessTest() {

    // Given
    Long userSeq = 25L;
    UserAddressRequest userAddressRequest = UserAddressRequest.builder()
        .post("54321")
        .spotX(152.157482231)
        .spotY(33.5486454853)
        .addressDetail("B동 201호")
        .defaultStatus(DefaultStatus.N)
        .build();

    // When
    userAddressService.registerAddress(userSeq, userAddressRequest);

    // Then
    List<UserAddressResponse> list = userAddressService.getUserAddress(userSeq);
    assertEquals(
        list.stream().filter(address -> UserAddressResponse.isDefault(address.getDefaultStatus()))
            .count(), 1);
    assertFalse(list.isEmpty());
    assertEquals(list.size(), 3);
  }

  @Test
  @DisplayName("회원 배달지 삭제 성공 테스트")
  public void deleteAddressSuccessTest() {

    // Given
    Long addressSeq = 4L;

    // When
    boolean isDelete = userAddressService.deleteAddress(addressSeq);

    //Then
    assertTrue(isDelete);
  }

  @Test
  @DisplayName("회원 배달지 삭제 실패 테스트")
  public void deleteAddressFailTest() {

    // Given
    Long addressSeq = 123L;

    // Then
    assertThrows(NotExistsAddressException.class,
        // When
        () -> userAddressService.deleteAddress(addressSeq));
  }

}
