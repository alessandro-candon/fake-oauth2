package com.alessandrocandon.fakeoauth2.service;

import com.alessandrocandon.fakeoauth2.dto.JwtToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.time.Instant;
import java.util.Map;

public class JwtService {

    private final Algorithm algorithm;
    private final Map<String, Object> headers;
    private final JsonNode payload;

    public JwtService(Algorithm algorithm, Map<String, Object> headers, JsonNode payload) {

        this.algorithm = algorithm;
        this.headers = headers;
        this.payload = payload;
    }

    public JwtService(Algorithm algorithm, Map<String, Object> headers) {

        this.algorithm = algorithm;
        this.headers = headers;
        this.payload = JsonNodeFactory.instance.objectNode();;
    }

    public JwtToken getToken() {
        String jwt = JWT.create()
                .withHeader(headers)
                .withPayload(payload.toPrettyString())
                .sign(algorithm);

        Long exp = payload.findValue("exp") != null ? payload.get("exp").asInt() : Instant.now().getEpochSecond();
        // todo : we can add a fake refresh token to return a real token.
        return new JwtToken(jwt,  exp);
    }
}
