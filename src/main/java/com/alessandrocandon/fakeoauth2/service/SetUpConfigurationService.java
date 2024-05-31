/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.service;

import com.alessandrocandon.fakeoauth2.dto.SetUpConfigurationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.LinkedHashMap;
import java.util.Map;

public final class SetUpConfigurationService {

  public static final String USER_ID_KEY = "fakeoauth2_user_key_id";
  private static int keyIndex;

  private static final Map<Integer, SetUpConfigurationDto> setUpConfigurationDtoHashMap =
      new LinkedHashMap<>();

  private SetUpConfigurationService() {}

  static {
    resetConfiguration();
  }

  public static Integer getLastKey() {
    return keyIndex > 0 ? keyIndex - 1 : 0;
  }

  public static SetUpConfigurationDto getLastConfiguration() {
    return setUpConfigurationDtoHashMap.get(getLastKey());
  }

  public static SetUpConfigurationDto getConfiguration(Integer id) {
    return setUpConfigurationDtoHashMap.get(id);
  }

  public static SetUpConfigurationDto getConfigurationByJwtPayload(String jwtJsonPayload)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jwtPayload = mapper.readTree(jwtJsonPayload);
    int userKey = jwtPayload.get(USER_ID_KEY).asInt();
    return setUpConfigurationDtoHashMap.get(userKey);
  }

  public static Map<Integer, SetUpConfigurationDto> getAllConfigurations() {
    return setUpConfigurationDtoHashMap;
  }

  public static void setConfiguration(SetUpConfigurationDto setUpConfigurationDto) {
    var accessTokenWithId =
        ((ObjectNode) setUpConfigurationDto.accessToken()).put(USER_ID_KEY, keyIndex);
    var idTokenWithId = ((ObjectNode) setUpConfigurationDto.idToken()).put(USER_ID_KEY, keyIndex);
    setUpConfigurationDtoHashMap.put(
        keyIndex,
        new SetUpConfigurationDto(setUpConfigurationDto.user(), accessTokenWithId, idTokenWithId));
    keyIndex = keyIndex + 1;
  }

  public static void deleteConfiguration(Integer id) {
    setUpConfigurationDtoHashMap.remove(id);
  }

  public static void resetConfiguration() {
    setUpConfigurationDtoHashMap.clear();
    keyIndex = 0;
    setUpConfigurationDtoHashMap.put(
        0,
        new SetUpConfigurationDto(
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode()));
  }
}
