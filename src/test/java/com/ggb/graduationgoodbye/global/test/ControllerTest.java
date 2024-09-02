package com.ggb.graduationgoodbye.global.test;

import com.ggb.graduationgoodbye.global.error.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
abstract public class ControllerTest {

  @Autowired
  protected MockMvc mvc;

  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders.standaloneSetup(initController())
        .setControllerAdvice(GlobalExceptionHandler.class)
        .setCustomArgumentResolvers()
        .build();
  }

  abstract protected Object initController();

}
