package com.alessandrocandon.fakeoauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public record SetUpConfigurationDto(
        @JsonProperty("user") JsonNode user,
        @JsonProperty("access_token") JsonNode accessToken,
        @JsonProperty("id_token") JsonNode idToken) {
}
