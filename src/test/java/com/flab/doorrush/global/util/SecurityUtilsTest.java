package com.flab.doorrush.global.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//@SpringBootTest
class SecurityUtilsTest {

  @Test
  @DisplayName("getEncryptedValue 메소드 성공 테스트 결과값으로 암호화된 문자열을 리턴한다")
  public void getEncryptedValueSuccessTest() {
    // Given
    String testValue = "yoopureum";
    // When
    String encryptedResult = SecurityUtils.getEncryptedValue(testValue);
    // Then
    assertEquals("co2DMEsntaXdDZpuwH9RUw==", encryptedResult);
  }

  @Test
  @DisplayName("getDecryptedValue 메소드 성공 테스트 결과값으로 복호화된 문자열을 리턴한다")
  public void getDecryptedValueSuccessTest() {
    // Given
    String testValue = "yoopureumtest";
    String encryptedValue = SecurityUtils.getEncryptedValue(testValue);
    // When
    String decryptedResult = SecurityUtils.getDecryptedValue(encryptedValue);
    // Then
    assertEquals(testValue, decryptedResult);
  }

}