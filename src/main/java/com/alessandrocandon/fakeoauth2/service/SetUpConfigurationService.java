/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.service;

import com.alessandrocandon.fakeoauth2.dto.SetUpConfigurationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.util.HashMap;
import java.util.Map;

public final class SetUpConfigurationService {

  public static final String USER_ID_KEY = "user_key_id";
  private static int keyIndex;

  private static final Map<Integer, SetUpConfigurationDto> setUpConfigurationDtoHashMap =
      new HashMap<>();

  private SetUpConfigurationService() {}

  static {
    resetConfiguration();
  }

  public static SetUpConfigurationDto getLastConfiguration() {
    return setUpConfigurationDtoHashMap.get(keyIndex);
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
    setUpConfigurationDtoHashMap.put(keyIndex, setUpConfigurationDto);
    keyIndex++;
  }

  public static void deleteConfiguration(Integer id) {
    setUpConfigurationDtoHashMap.remove(id);
  }

  public static void resetConfiguration() {
    keyIndex = 0;
    setUpConfigurationDtoHashMap.put(
            0,
            new SetUpConfigurationDto(
                    JsonNodeFactory.instance.objectNode(),
                    JsonNodeFactory.instance.objectNode(),
                    JsonNodeFactory.instance.objectNode()));
  }
}
