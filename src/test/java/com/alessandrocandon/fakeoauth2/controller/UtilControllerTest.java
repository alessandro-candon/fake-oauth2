package com.alessandrocandon.fakeoauth2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureMockMvc
class UtilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String USER_JSON = """
            {"user_id":"583c3ac3f38e84297c002546","email":"test@test.com","name":"test@test.com","given_name":"Hello","family_name":"Test","nickname":"test","last_ip":"94.121.163.63","logins_count":15,"email_verified":true}
            """;

    @Test
    void userInfoFullFlow() throws Exception {
        mockMvc
                .perform(post("/userinfo")
                        .content(USER_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk());

        mockMvc
                .perform(get("/userinfo")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains(USER_JSON);

        mockMvc
                .perform(delete("/userinfo")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains("0");
    }

    @Test
    void postJwtPayload() throws Exception {
        mockMvc
                .perform(post("/as/token.oauth2/payload")
                        .content(USER_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk());
    }

    @Test
    void resetJwtPayload() throws Exception {
        mockMvc
                .perform(delete("/as/token.oauth2/payload")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk());
    }
}
