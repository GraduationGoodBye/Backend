package com.ggb.graduationgoodbye.domain.member.utils;

import org.springframework.stereotype.Component;

@Component
public class NicknameGenerator {

  private final String[] NAMES = {"Vinci", "Botticelli", "Angel", "Caravaggio", "Rembrandt",
      "Pollock", "Vermeer", "Delacroix", "Monet", "Gogh", "Klimt", "Picasso", "Dali", "Warhol",
      "Kusama"};

  public String generate() {
    return randomName() + randomNums();
  }

  private int randomNums() {
    return (int) (Math.random() * Math.pow(10, 4));
  }

  private String randomName() {
    return NAMES[(int) (Math.random() * NAMES.length)];
  }
}
