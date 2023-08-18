package com.alessandrocandon.fakeoauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JwtToken(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") Long expiresIn,
        @JsonProperty("refresh_token") String refreshToken) {
    public JwtToken(String accessToken, Long expiresIn) {
        this(accessToken, "Bearer", expiresIn, "fake-refresh-token");
    }
}
