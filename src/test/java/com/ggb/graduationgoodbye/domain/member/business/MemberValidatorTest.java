package com.ggb.graduationgoodbye.domain.member.business;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import com.ggb.graduationgoodbye.domain.member.common.exception.InvalidSnsTypeException;
import com.ggb.graduationgoodbye.global.test.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class MemberValidatorTest extends ServiceTest {

  private MemberValidator memberValidator;

  @BeforeEach
  void setUp() {
    memberValidator = new MemberValidator();
  }

  @ParameterizedTest
  @EnumSource(SnsType.class)
  void validateSnsType_성공_대문자(SnsType snsType) {
    assertDoesNotThrow(() -> memberValidator.validateSnsType(snsType.name().toUpperCase()));
  }

  @ParameterizedTest
  @EnumSource(SnsType.class)
  void validateSnsType_성공_소문자(SnsType snsType) {
    assertDoesNotThrow(() -> memberValidator.validateSnsType(snsType.name().toLowerCase()));
  }

  @Test
  void validateSnsType_실패_null() {
    assertThrows(InvalidSnsTypeException.class, () -> memberValidator.validateSnsType(null));
  }

  @Test
  void validateSnsType_실패_빈값() {
    assertThrows(InvalidSnsTypeException.class, () -> memberValidator.validateSnsType(""));
  }

  @Test
  void validateSnsType_실패_공백() {
    assertThrows(InvalidSnsTypeException.class, () -> memberValidator.validateSnsType(" "));
  }

  @Test
  void validateSnsType_실패_다른타입() {
    assertThrows(InvalidSnsTypeException.class, () -> memberValidator.validateSnsType("FACEBOOK"));
  }

  @Test
  void validateSnsType_실패_글자사이공백() {
    assertThrows(InvalidSnsTypeException.class, () -> memberValidator.validateSnsType("goo gle"));
  }
}