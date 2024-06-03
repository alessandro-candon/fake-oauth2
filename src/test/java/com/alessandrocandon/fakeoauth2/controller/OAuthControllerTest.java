/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
class OAuthControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void testGetToken() throws Exception {
    mockMvc.perform(post("/as/token.oauth2").content("")).andExpect(status().isOk());
  }

  @Test
  void testRedirect() throws Exception {
    mockMvc
        .perform(
            get("/as/authorization.oauth2")
                .queryParam("redirect_uri", "http://localhost/redirect")
                .queryParam("state", "state"))
        .andExpect(status().is3xxRedirection());
  }

  @Test
  void testJwks() throws Exception {
    mockMvc.perform(get("/ext/jwks")).andExpect(status().isOk());
  }

  @Test
  void testWellKnown() throws Exception {
    var wellKnownConfig = FileUtil.getResourceFileAsString("fixtures/configuration_wellknown.json");
    mockMvc
        .perform(
            post("/well-known")
                .content(wellKnownConfig)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());

    mockMvc.perform(get("/.well-known/openid-configuration")).andExpect(status().isOk());
  }
}
