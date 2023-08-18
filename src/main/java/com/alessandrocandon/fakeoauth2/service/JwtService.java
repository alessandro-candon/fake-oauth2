package com.alessandrocandon.fakeoauth2.service;

import com.alessandrocandon.fakeoauth2.dto.JwtToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Autowired @Qualifier("fakeoauth2_ikeyservice") IKeyService iKeyService;
    @Autowired UserService userService;

    public JwtToken getToken(Map<String, Object> headers) {

        var payload = userService.getJwtPayload();

        String jwt =
                JWT.create()
                        .withHeader(headers)
                        .withPayload(payload.toPrettyString())
                        .sign(iKeyService.getAlgorithm());

        Long exp =
                payload.findValue("exp") != null
                        ? payload.get("exp").asInt()
                        : Instant.now().getEpochSecond();
        // todo : we can add a fake refresh token to return a real token.
        return new JwtToken(jwt, exp);
    }

    public String getDecodedPayload(String authorizationHeader) {
        String payload = JWT.decode(authorizationHeader.replace("Bearer ", "")).getPayload();
        return new String(Base64.getDecoder().decode(payload));
    }
}
