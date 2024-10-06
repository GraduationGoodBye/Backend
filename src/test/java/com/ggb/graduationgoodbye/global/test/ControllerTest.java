package com.ggb.graduationgoodbye.global.test;

import com.ggb.graduationgoodbye.domain.auth.common.interceptor.AuthInterceptor;
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

  @Autowired
  protected AuthInterceptor authInterceptor;

  @MockBean
  protected TokenService tokenService;

  protected final Gson gson = new Gson();

  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders.standaloneSetup(initController())
        .setControllerAdvice(GlobalExceptionHandler.class)
        .addInterceptors(authInterceptor)
        .build();
  }

  abstract protected Object initController();

}
