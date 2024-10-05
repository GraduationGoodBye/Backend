package com.ggb.graduationgoodbye.global.util.test2;

import static com.ggb.graduationgoodbye.global.util.test2.RandomValue.random;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.NoArgsConstructor;

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
