/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alessandrocandon.fakeoauth2.common.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureMockMvc
class UserInfoControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void testPostUserInfo() throws Exception {
    mockMvc
        .perform(
            post("/idp/userinfo.openid")
                .content("")
                .header(
                    HttpHeaders.AUTHORIZATION,
                        "Bearer " + FileUtil.getResourceFileAsString("fixtures/bearer_0.txt")))
        .andExpect(status().isOk());
  }

  @Test
  void testGetUserInfo() throws Exception {
    mockMvc
            .perform(
                    get("/idp/userinfo.openid")
                            .content("")
                            .header(
                                    HttpHeaders.AUTHORIZATION,
                                    "Bearer " + FileUtil.getResourceFileAsString("fixtures/bearer_0.txt")))
            .andExpect(status().isOk());
  }

  @Test
  void testDeleteUserInfoFail() throws Exception {
    mockMvc
            .perform(
                    delete("/idp/userinfo.openid")
                            .content("")
                            .header(
                                    HttpHeaders.AUTHORIZATION,
                                    "Bearer " + FileUtil.getResourceFileAsString("fixtures/bearer_0.txt")))
                            .andExpect(status().is4xxClientError());
  }
}
