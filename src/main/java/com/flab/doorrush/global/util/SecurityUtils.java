package com.flab.doorrush.global.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecurityUtils {

  static final String ALG = "AES/CBC/PKCS5Padding";
  static final String AESKEY = "flabdoorrushboombayahboombayahbo";// 32byte
  static final String AESIV = "jdpureummyeonjae"; //16byte
  static final String ALGORITHM_NAME = "AES";

  public static String getEncryptedValue(String value) {
    try {
      Cipher cipher = Cipher.getInstance(SecurityUtils.ALG);
      SecretKeySpec keySpec = new SecretKeySpec(SecurityUtils.AESKEY.getBytes(),
          SecurityUtils.ALGORITHM_NAME);
      IvParameterSpec ivParameterSpec = new IvParameterSpec(SecurityUtils.AESIV.getBytes());
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
      // 암호화 실행 및 인코딩 설정값 입력
      byte[] encryptedValue = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(encryptedValue);
    } catch (Exception e) {
      log.error("해당 값의 복호화 실패로 예외가 발생하였습니다.", e);
      return null;
    }
  }


  public static String getDecryptedValue(String encryptedValue) {
    try {
      Cipher cipher = Cipher.getInstance(SecurityUtils.ALG);
      SecretKeySpec secretKeySpec = new SecretKeySpec(SecurityUtils.AESKEY.getBytes(),
          SecurityUtils.ALGORITHM_NAME);
      IvParameterSpec ivParameterSpec = new IvParameterSpec(SecurityUtils.AESIV.getBytes());
      // 복호화 실행 및 인코딩 설정값 입력
      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
      byte[] decodedBytes = Base64.getDecoder().decode(encryptedValue);
      byte[] decryptedBytes = cipher.doFinal(decodedBytes);
      return new String(decryptedBytes);
    } catch (Exception e) {
      log.error("해당 값의 복호화 실패로 예외가 발생하였습니다.", e);
      return null;
    }
  }
}
