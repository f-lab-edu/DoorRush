package com.flab.doorrush.domain.user.api;

import com.flab.doorrush.domain.user.dto.request.ChangePasswordRequest;
import com.flab.doorrush.domain.user.dto.request.JoinUserRequest;
import com.flab.doorrush.domain.user.dto.request.UserAddressRequest;
import com.flab.doorrush.global.Response.BasicResponse;
import com.flab.doorrush.domain.user.dto.response.JoinUserResponse;
import com.flab.doorrush.domain.user.dto.response.UserAddressResponse;
import com.flab.doorrush.domain.user.service.UserAddressService;
import com.flab.doorrush.domain.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
// @RequiredArgsConstructor : 초기화되지 않은 final 필드를 매개변수로 받는 생성자를 생성하는 어노테이션입니다.
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserAddressService userAddressService;

  @PostMapping
  public ResponseEntity<BasicResponse<JoinUserResponse>> joinUser(
      @Valid @RequestBody JoinUserRequest joinUserRequest) {
    JoinUserResponse userResponse = userService.joinUser(joinUserRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(BasicResponse.success(userResponse));
  }

  @PatchMapping("/{userSeq}/password")
  public ResponseEntity<BasicResponse<Boolean>> changePassword(@PathVariable Long userSeq,
      @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(BasicResponse.success(userService.changePassword(userSeq, changePasswordRequest)));
  }

  @GetMapping("/{userSeq}/addresses")
  public ResponseEntity<BasicResponse<UserAddressResponse>> getAddress(@PathVariable Long userSeq) {
    UserAddressResponse userAddressResponse = UserAddressResponse.builder()
        .userAddresses(userAddressService.getUserAddress(userSeq)).build();
    return ResponseEntity.status(HttpStatus.OK).body(BasicResponse.success(userAddressResponse));
  }

  @PostMapping("/{userSeq}/addresses")
  public ResponseEntity<BasicResponse<UserAddressResponse>> registerAddress(
      @PathVariable Long userSeq,
      @Valid @RequestBody UserAddressRequest userAddressRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            BasicResponse.success(userAddressService.registerAddress(userSeq, userAddressRequest)));
  }

  @DeleteMapping("/{userSeq}/addresses/{addressSeq}")
  public ResponseEntity<BasicResponse<Boolean>> deleteAddress(@PathVariable Long addressSeq) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(BasicResponse.success(userAddressService.deleteAddress(addressSeq)));
  }
}
