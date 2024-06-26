/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.service;

import static com.alessandrocandon.fakeoauth2.service.SetUpConfigurationService.USER_ID_KEY;
import static org.assertj.core.api.Assertions.assertThat;

import com.alessandrocandon.fakeoauth2.dto.SetUpConfigurationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SetUpConfigurationServiceTest {

  @BeforeEach
  void setUp() {
    SetUpConfigurationService.resetConfiguration();
  }

  @Test
  void getLastKey() {
    var lastK = SetUpConfigurationService.getLastKey();
    assertThat(lastK).isZero();

    SetUpConfigurationService.setConfiguration(
        new SetUpConfigurationDto(
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode()));
    lastK = SetUpConfigurationService.getLastKey();
    assertThat(lastK).isZero();

    SetUpConfigurationService.setConfiguration(
        new SetUpConfigurationDto(
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode()));
    lastK = SetUpConfigurationService.getLastKey();
    assertThat(lastK).isEqualTo(1);
  }

  @Test
  void getLastConfiguration() {
    SetUpConfigurationService.setConfiguration(
        new SetUpConfigurationDto(
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode()));
    var lastC = SetUpConfigurationService.getLastConfiguration();
    assertThat(lastC.accessToken()).isInstanceOf(JsonNode.class);
    assertThat(lastC.idToken()).isInstanceOf(JsonNode.class);
    assertThat(lastC.user()).isInstanceOf(JsonNode.class);

    assertThat(lastC.accessToken().get(USER_ID_KEY).asInt()).isZero();
    assertThat(lastC.idToken().get(USER_ID_KEY).asInt()).isZero();
  }

  @Test
  void getConfiguration() {
    SetUpConfigurationService.setConfiguration(
        new SetUpConfigurationDto(
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode()));
    SetUpConfigurationService.setConfiguration(
        new SetUpConfigurationDto(
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode()));
    SetUpConfigurationService.setConfiguration(
        new SetUpConfigurationDto(
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode()));

    var firstC = SetUpConfigurationService.getConfiguration(0);
    assertThat(firstC.accessToken()).isInstanceOf(JsonNode.class);
    assertThat(firstC.idToken()).isInstanceOf(JsonNode.class);
    assertThat(firstC.user()).isInstanceOf(JsonNode.class);

    assertThat(firstC.accessToken().get(USER_ID_KEY).asInt()).isZero();
    assertThat(firstC.idToken().get(USER_ID_KEY).asInt()).isZero();

    var conf = SetUpConfigurationService.getConfiguration(2);
    assertThat(conf.accessToken()).isInstanceOf(JsonNode.class);
    assertThat(conf.idToken()).isInstanceOf(JsonNode.class);
    assertThat(conf.user()).isInstanceOf(JsonNode.class);

    assertThat(conf.accessToken().get(USER_ID_KEY).asInt()).isEqualTo(2);
    assertThat(conf.idToken().get(USER_ID_KEY).asInt()).isEqualTo(2);
  }

  @Test
  void getConfigurationByJwtPayload() throws JsonProcessingException {
    SetUpConfigurationService.setConfiguration(
        new SetUpConfigurationDto(
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode()));
    var conf =
        SetUpConfigurationService.getConfigurationByJwtPayload(
            """
                        {
                          "scope": [
                            "openid",
                            "profile"
                          ],
                          "authorization_details": [],
                          "client_id": "my",
                          "iss": "test.iss.test",
                          "jti": "jit",
                          "sub": "asdasd",
                          "uid": "asdasd",
                          "origin": "asdasd",
                          "iat": 1717063455,
                          "uuid": "asdasdasd",
                          "exp": 1717070655,
                          "fakeoauth2_user_key_id": 0
                        }
                        """);

    assertThat(conf.accessToken().get(USER_ID_KEY).asInt()).isZero();
  }

  @Test
  void getConfigurationByJwtPayloadNotFound() throws JsonProcessingException {
    SetUpConfigurationService.setConfiguration(
        new SetUpConfigurationDto(
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode()));
    var conf =
        SetUpConfigurationService.getConfigurationByJwtPayload(
            """
                        {
                          "scope": [
                            "openid",
                            "profile"
                          ],
                          "authorization_details": [],
                          "client_id": "my",
                          "iss": "test.iss.test",
                          "jti": "jit",
                          "sub": "asdasd",
                          "uid": "asdasd",
                          "origin": "asdasd",
                          "iat": 1717063455,
                          "uuid": "asdasdasd",
                          "exp": 1717070655,
                          "fakeoauth2_user_key_id": 1
                        }
                        """);

    assertThat(conf).isNull();
  }

  @Test
  void setConfiguration() {
    SetUpConfigurationService.setConfiguration(
        new SetUpConfigurationDto(
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode()));
    var conf = SetUpConfigurationService.getConfiguration(0);
    assertThat(conf).isNotNull();
  }

  @Test
  void deleteConfiguration() {
    SetUpConfigurationService.setConfiguration(
        new SetUpConfigurationDto(
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode()));
    SetUpConfigurationService.setConfiguration(
        new SetUpConfigurationDto(
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode(),
            JsonNodeFactory.instance.objectNode()));
    assertThat(SetUpConfigurationService.getAllConfigurations()).hasSize(2);

    SetUpConfigurationService.deleteConfiguration(0);
    SetUpConfigurationService.deleteConfiguration(1);

    assertThat(SetUpConfigurationService.getAllConfigurations()).isEmpty();
  }

  @Test
  void testWellKnownFlow() {
    SetUpConfigurationService.setOAuthWellKnow(
        JsonNodeFactory.instance.objectNode().arrayNode().add("test"));
    var conf = SetUpConfigurationService.getOAuthWellKnow();
    assertThat(conf).isNotNull();
  }
}
