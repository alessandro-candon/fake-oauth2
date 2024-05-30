/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.service;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserServiceTest {

  UserService userService;

  @BeforeEach
  void setUp() {
    userService = new UserService();
  }

  @Test
  void userFlowTest() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode json = mapper.readTree("{\"test\":\"abc\"}");
    userService.setUser(json);
    assertEquals(json, userService.getLastUser());
    assertEquals(json, userService.getUser(1));
    assertEquals(json, userService.getUserByJwtPayload("{\"user_key_id\":1}"));
    assertEquals(1, userService.getCurrentUserKey());
  }

  @Test
  void jwtPayloadTest() {
    var emptyNode = JsonNodeFactory.instance.objectNode();
    userService.setAccessTokenPayload(emptyNode);
    var jwtPayload = userService.getAccessTokenPayload();
    assertEquals(jwtPayload.get(UserService.USER_PAYLOAD_KEY).asInt(), 0);
  }
}
