package com.ggb.graduationgoodbye.global.util.test2;

import static com.ggb.graduationgoodbye.global.util.test2.RandomValue.random;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.NoArgsConstructor;

/**
 * String의 값을 생성하는 로직이 작성되어 있음
 * 변수로 선언되어있는 nullable , size , languages 를 참조하여 String를 생성하기에
 * String 생성마다 StringGenerator를 새롭게 호출해주어야 함.

 * 현재는 랜덤한 문자열 , 랜덤한 영문 문자열 두 가지만 생성이 가능함.
 */
@NoArgsConstructor
public class StringGenerator {

  private static final String english = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  private boolean nullable = true;
  private List<Language> languages = new ArrayList<>();

  private List<String> charsetList = new ArrayList<>(Charset.availableCharsets().keySet());
  private int minSize = 0;
  private int maxSize = 2000;

  public StringGenerator(int minSize , int maxSize) {
    this.minSize = minSize;
    this.maxSize = Math.max(maxSize, minSize+1);
  }

  public StringGenerator(int maxSize) {
    this.maxSize = Math.max(maxSize, 1);
  }

  public StringGenerator setNullable(boolean nullable) {
    this.nullable = nullable;
    return this;
  }

  public StringGenerator setLanguages(Language[] language) {
    languages.addAll(Arrays.asList(language));
    return this;
  }

  public StringGenerator setLanguages(Language language) {
    languages.add(language);
    return this;
  }

  public String get() {
    int size = nullable ? random.nextInt(maxSize) : random.nextInt(minSize, maxSize);
    return languages.isEmpty() ? truncatedString(size) : randomString(languages, size);
  }

  public String getEmail() {
    String email = randomEnglish(10);
    String domain = randomEnglish(5);
    String subDomain = randomEnglish(3);
    return email + "@" + domain + "." + subDomain;
  }









  public static String randomEnglish(int size) {
    if (size < 0) return null;
    StringBuilder sb = new StringBuilder(size);

    for (int i = 0; i < size; i++) {
      int index = random.nextInt(english.length());
      sb.append(english.charAt(index));
    }

    return sb.toString();
  }


  /**
   * 한국어 문자열 생성은 추가 예정 ,
   * 현재 Korean 을 지정 후 랜덤 값 생성시, "korean" 이라는 고정 문자열 반환
   */
  private String randomString(List<Language> languageList, int size){
    int index = random.nextInt(languageList.size());
    Language language = languageList.get(index);

    return switch (language) {
      case KOREAN -> "korean";
      case ENGLISH -> randomEnglish(size);
      default -> null;
    };
  }

  private String truncatedString(int size) {
    if (size < 0) return null;
    byte[] randomByte = randomByte(size);
    Charset charset = randomCharset();
    String str = new String(randomByte, charset);
    return str.length() < size ? str : str.substring(0, size);
  }
  private static byte[] randomByte(int size) {
    byte[] array = new byte[size];
    for (int i = 0; i < size; i++ ) {
      array[i] = (byte) random.nextInt(32, 126);
    }
    return array;
  }

  private Charset randomCharset() {
    int randomIndex = random.nextInt(charsetList.size());
    return Charset.forName(charsetList.get(randomIndex));
  }
}
