/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.service;

import com.alessandrocandon.fakeoauth2.dto.JwtToken;
import com.auth0.jwt.JWT;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final IKeyService iKeyService;

  private final UserService userService;

  public JwtService(UserService userService, IKeyService iKeyService) {
    this.userService = userService;
    this.iKeyService = iKeyService;
  }

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

    return new JwtToken(jwt, jwt, exp);
  }

  public String getDecodedPayload(String authorizationHeader) {
    String payload = JWT.decode(authorizationHeader.replace("Bearer ", "")).getPayload();
    return new String(Base64.getDecoder().decode(payload));
  }
}
