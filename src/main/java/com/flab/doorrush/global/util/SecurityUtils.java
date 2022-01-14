package com.flab.doorrush.global.util;

import com.flab.doorrush.global.exception.DecryptionByAES256FailException;
import com.flab.doorrush.global.exception.EncryptionByAES256FailException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

  final static String ALG = "AES/CBC/PKCS5Padding";
  final static String AESKEY = "flabdoorrushboombayahboombayahbo";// 32byte
  final static String AESIV = "jdpureummyeonjae"; //16byte

  public static String getEncryptedValue(String value) {
    String encryptedValueToString = "";
    try {
      Cipher cipher = Cipher.getInstance(ALG);
      SecretKeySpec keySpec = new SecretKeySpec(AESKEY.getBytes(), "AES");
      IvParameterSpec ivParameterSpec = new IvParameterSpec(AESIV.getBytes());
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
      // 암호화 실행 및 인코딩 설정값 입력
      byte[] encryptedValue = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
      encryptedValueToString = Base64.getEncoder().encodeToString(encryptedValue);
    } catch (Exception e) {
      throw new EncryptionByAES256FailException("해당 값의 암호화 실패로 예외가 발생하였습니다.", e);
    } finally {
      return encryptedValueToString;
    }
  }


  public static String getDecryptedValue(String encryptedValue) {
    String decryptedValue = "";
    try {
      Cipher cipher = Cipher.getInstance(ALG);
      SecretKeySpec secretKeySpec = new SecretKeySpec(AESKEY.getBytes(), "AES");
      IvParameterSpec ivParameterSpec = new IvParameterSpec(AESIV.getBytes());
      // 복호화 실행 및 인코딩 설정값 입력
      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
      byte[] decodedBytes = Base64.getDecoder().decode(encryptedValue);
      byte[] decryptedBytes = cipher.doFinal(decodedBytes);
      decryptedValue = new String(decryptedBytes);
    } catch (Exception e) {
      throw new DecryptionByAES256FailException("해당 값의 복호화 실패로 예외가 발생하였습니다.", e);
    } finally {
      return decryptedValue;
    }
  }
}
