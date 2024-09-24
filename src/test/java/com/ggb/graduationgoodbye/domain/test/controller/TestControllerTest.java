package com.ggb.graduationgoodbye.domain.test.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ggb.graduationgoodbye.domain.test.service.TestService;
import com.ggb.graduationgoodbye.global.test.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@WebMvcTest(TestController.class)
class TestControllerTest extends ControllerTest {

  @MockBean
  private TestService testService;

  @Override
  protected Object initController() {
    return new TestController(testService);
  }

  @Test
  void 정상_연결확인() throws Exception {
    mvc.perform(get("/test/check"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(HttpStatus.OK.name()))
        .andExpect(jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()));
  }

  @Test
  void 정상_Base64_encode() throws Exception {
    String data = "data";
    String encodedData = "encodedData";
    when(testService.encode(any(String.class))).thenReturn(encodedData);

    mvc.perform(post("/test/base64/encode")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(data)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(HttpStatus.OK.name()))
        .andExpect(jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.data").value(encodedData));

    verify(testService, times(1)).encode(any(String.class));
  }
}