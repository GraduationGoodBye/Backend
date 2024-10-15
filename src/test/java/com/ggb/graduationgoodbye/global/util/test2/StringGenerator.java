package com.ggb.graduationgoodbye.global.util.test2;

import static com.ggb.graduationgoodbye.global.util.test2.RandomValue.random;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.NoArgsConstructor;

/**
 * String의 값을 생성하는 로직이 작성되어 있음
 * 별도의 설정이 없을 경우 생성되는 문자열은 , null값 허용, 문자열 랜덤, 최소 길이 0 , 최대 길이 200
 */
@NoArgsConstructor
public class StringGenerator {

  private static final String english = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  private boolean nullable = true;
  private List<Language> languages = new ArrayList<>();

  private List<String> charsetList = new ArrayList<>(Charset.availableCharsets().keySet());
  private int minSize = 0;
  private int maxSize = 2000;

  /**
   * 생성되는 문자열의 최소, 최대 길이를 지정하기 위한 생성자
   */

  public StringGenerator(int minSize , int maxSize) {
    this.minSize = minSize;
    this.maxSize = Math.max(maxSize, minSize+1);
  }


  /**
   * 생성되는 문자열의 최소 길이를 지정하기 위한 생성자
   */
  
  public StringGenerator(int maxSize) {
    this.maxSize = Math.max(maxSize, 1);
  }

  /**
   * 생성되는 문자열의 null 값 여부를 지정하기 위한 메소드
   *
   * null 값이 허용되지 않는 필드의 경우
   * StringGenerator.setNullable(fasle);
   * 와 같이 랜덤한 문자열에 필수적으로 값이 생성되도록 할 수 있음
   */

  public StringGenerator setNullable(boolean nullable) {
    this.nullable = nullable;
    return this;
  }

  /**
   * 생성되는 문자열의 언어를 지정하기 위한 메소드
   *
   * Language[] 배열 내부에, 지정할 다수의 언어를 입력 후 .setLanguages() 로 전달하면
   * 새롭게 생성되는 문자열은 Language[] 내부에 입력되어 있는 언어 중 하나로 생성됨
   */
  public StringGenerator setLanguages(Language[] language) {
    languages.addAll(Arrays.asList(language));
    return this;
  }

  /**
   * 생성되는 문자열의 언어를 지정하기 위한 메소드
   *
   * Language 중 하나를 지정하여 .setLanguages() 로 전달하면
   * 새롭게 생성되는 문자열은 해당 언어로만 생성 됨
   */
  public StringGenerator setLanguages(Language language) {
    languages.add(language);
    return this;
  }

  /**
   * 실제 랜덤한 String 값을 생성하는 메소드
   *
   * 해당 클래스에 작성되어있는 최소,최대 길이, null 여부, 문자열 설정을 읽고 그에 맞춰 랜덤한 문자열을 생성 후 반환함
   * 별도의 설정이 없을 경우 생성되는 문자열은 , null값 허용, 문자열 랜덤, 최소 길이 0 , 최대 길이 200
   * 변경이 필요할 경우, 해당 클래스의 생성자와 메소드를 참고하길 바람
   */
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
