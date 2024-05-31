/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alessandrocandon.fakeoauth2.common.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureMockMvc
class UtilControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void setupConfigurationFullFlow() throws Exception {
    mockMvc
        .perform(
            post("/configurations")
                .content(FileUtil.getResourceFileAsString("fixtures/configuration_body.json"))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());

    mockMvc
        .perform(get("/configurations").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString()
        .contains("583c3ac3f38e84297c002546");

    mockMvc
        .perform(get("/configurations/0").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString()
        .contains("583c3ac3f38e84297c002546");

    mockMvc
        .perform(delete("/configurations/0").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }
}
