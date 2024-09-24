package com.ggb.graduationgoodbye.domain.member.business;

import org.springframework.stereotype.Component;

@Component
public class NicknameGenerator {

  private static final String[] ADJECTIVE = {
      "행복한", "큰", "작은", "아름다운", "강한", "똑똑한", "빠른", "밝은", "단단한", "친근한", "재미있는",
      "부유한", "가난한", "게으른", "깨끗한", "어두운", "따뜻한", "차가운", "오래된", "젊은"
  };

  private static final String[] NOUNS = {
      "김홍도", "신윤복", "정선", "김정희", "장승업", "이중섭", "박수근", "김환기", "천경자", "박래현",
      "오지호", "이인성", "이상범", "변월룡", "권진규", "황주리", "윤형근", "김창열", "박서보", "이우환",
      "최욱경", "이강소", "김종학", "문신", "백남준", "고영훈", "레오나르도", "미켈란젤로", "라파엘로",
      "렘브란트", "고흐", "모네", "마네", "세잔", "피카소", "달리", "마티스", "뭉크", "칼로", "폴록",
      "워홀", "클림트", "오키프", "마그리트", "샤갈", "실레", "말레비치", "칸딘스키", "고갱", "드가"
  };

  public String generate() {
    return randomAdjective() + randomNoun();
  }

  private String randomAdjective() {
    return ADJECTIVE[(int) (Math.random() * ADJECTIVE.length)];
  }

  private String randomNoun() {
    return NOUNS[(int) (Math.random() * NOUNS.length)];
  }
}
