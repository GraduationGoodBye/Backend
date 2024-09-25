package com.ggb.graduationgoodbye.domain.member.business;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import com.ggb.graduationgoodbye.domain.member.common.exception.InvalidSnsTypeException;
import com.ggb.graduationgoodbye.global.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Spy;

public class MemberValidatorTest extends ServiceTest {

  @Spy
  private MemberValidator memberValidator;

  @ParameterizedTest
  @EnumSource(SnsType.class)
  void validateSnsType_성공_대문자(SnsType snsType) {
    // when then
    assertDoesNotThrow(() -> memberValidator.validateSnsType(snsType.name().toUpperCase()));
  }

  @ParameterizedTest
  @EnumSource(SnsType.class)
  void validateSnsType_성공_소문자(SnsType snsType) {
    // when then
    assertDoesNotThrow(() -> memberValidator.validateSnsType(snsType.name().toLowerCase()));
  }

  @Test
  void validateSnsType_실패_null() {
    // when then
    assertThrows(InvalidSnsTypeException.class, () -> memberValidator.validateSnsType(null));
  }

  @Test
  void validateSnsType_실패_빈값() {
    // when then
    assertThrows(InvalidSnsTypeException.class, () -> memberValidator.validateSnsType(""));
  }

  @Test
  void validateSnsType_실패_공백() {
    // when then
    assertThrows(InvalidSnsTypeException.class, () -> memberValidator.validateSnsType(" "));
  }

  @Test
  void validateSnsType_실패_다른타입() {
    // when then
    assertThrows(InvalidSnsTypeException.class, () -> memberValidator.validateSnsType("FACEBOOK"));
  }

  @Test
  void validateSnsType_실패_글자사이공백() {
    // when then
    assertThrows(InvalidSnsTypeException.class, () -> memberValidator.validateSnsType("goo gle"));
  }
}