package com.ggb.graduationgoodbye.global.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ggb.graduationgoodbye.domain.commonCode.controller.MajorController;
import com.ggb.graduationgoodbye.domain.commonCode.controller.UniversityController;
import com.ggb.graduationgoodbye.domain.member.controller.MemberController;
import com.ggb.graduationgoodbye.domain.test.controller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ContextLoadTest extends IntegrationTest {

  @Autowired
  private TestController testController;
  @Autowired
  private MemberController memberController;
  @Autowired
  private UniversityController universityController;
  @Autowired
  private MajorController majorController;

  @Test
  public void contextLoads() {
    assertNotNull(testController);
    assertNotNull(memberController);
    assertNotNull(universityController);
    assertNotNull(majorController);
  }

}
