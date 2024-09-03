package com.ggb.graduationgoodbye.global.test;

import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.global.error.GlobalExceptionHandler;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
@TestPropertySource(properties = "logging.config=classpath:logback-spring-test.xml")
abstract public class ControllerTest {

  @Autowired
  protected MockMvc mvc;

  protected final Gson gson = new Gson();

  @MockBean
  protected TokenService tokenService;

  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders.standaloneSetup(initController())
        .setControllerAdvice(GlobalExceptionHandler.class)
        .build();
  }

  abstract protected Object initController();

}
