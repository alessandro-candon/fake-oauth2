/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JwtToken(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("id_token") String idToken,
    @JsonProperty("token_type") String tokenType,
    @JsonProperty("expires_in") Long expiresIn,
    @JsonProperty("refresh_token") String refreshToken) {
  public JwtToken(String accessToken, String idToken, Long expiresIn) {
    this(accessToken, idToken, "Bearer", expiresIn, "fake-refresh-token");
  }
}
