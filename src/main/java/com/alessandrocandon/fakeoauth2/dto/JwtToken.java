package com.alessandrocandon.fakeoauth2.dto;

public record JwtToken(String accessToken, String tokenType, Long expiresIn, String refreshToken) {

    public JwtToken(String accessToken, Long expiresIn){
        this(accessToken, "Bearer", expiresIn, "fake-refresh-token");
    }
}