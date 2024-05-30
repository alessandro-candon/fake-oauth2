/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Map;

public class UserService {

  public static final String USER_PAYLOAD_KEY = "user_key_id";
  private static int userKeyIndex;
  private static JsonNode accessTokenPayload;
  private static JsonNode idTokenPayload;
  private static final Map<Integer, JsonNode> userWithKey = new HashMap<>();

  public UserService() {
    initUsers();
    initJwtPayload();
  }

  public JsonNode getUser(Integer userKeyIndex) {
    return userWithKey.get(userKeyIndex);
  }

  public JsonNode getLastUser() {
    return userWithKey.get(getCurrentUserKey());
  }

  public int getCurrentUserKey() {
    // because is the last user setted
    return userKeyIndex - 1;
  }

  public JsonNode getUserByJwtPayload(String jwt) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jwtPayload = mapper.readTree(jwt);
    int userKey = jwtPayload.get(USER_PAYLOAD_KEY).asInt();
    return userWithKey.get(userKey);
  }

  public Map<Integer, JsonNode> getAllUsers() {
    return userWithKey;
  }

  public void setUser(JsonNode user) {
    userWithKey.put(userKeyIndex, user);
    userKeyIndex++;
  }

  public JsonNode getAccessTokenPayload() {
    return accessTokenPayload;
  }

  public void setAccessTokenPayload(JsonNode payload) {
    ((ObjectNode) payload).put(UserService.USER_PAYLOAD_KEY, getCurrentUserKey());
    UserService.accessTokenPayload = payload;
  }

  public void initUsers() {
    userKeyIndex = 0;
    userWithKey.clear();
    setUser(JsonNodeFactory.instance.objectNode());
  }

  public void initJwtPayload() {
    accessTokenPayload = JsonNodeFactory.instance.objectNode();
  }
}
