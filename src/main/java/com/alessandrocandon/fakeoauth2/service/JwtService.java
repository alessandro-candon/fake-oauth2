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

  public JwtService(IKeyService iKeyService) {
    this.iKeyService = iKeyService;
  }

  public JwtToken getToken(Map<String, Object> headers) {

    var setUpConfigurationDto = SetUpConfigurationService.getLastConfiguration();

    String accessToken =
        JWT.create()
            .withHeader(headers)
            .withPayload(setUpConfigurationDto.accessToken().toPrettyString())
            .sign(iKeyService.getAlgorithm());

    String idToken =
        JWT.create()
            .withHeader(headers)
            .withPayload(setUpConfigurationDto.idToken().toPrettyString())
            .sign(iKeyService.getAlgorithm());

    Long exp =
        setUpConfigurationDto.accessToken().findValue("exp") != null
            ? setUpConfigurationDto.accessToken().get("exp").asInt()
            : Instant.now().getEpochSecond();

    return new JwtToken(accessToken, idToken, exp);
  }

  public String getDecodedPayload(String authorizationHeader) {
    String payload = JWT.decode(authorizationHeader.replace("Bearer ", "")).getPayload();
    return new String(Base64.getDecoder().decode(payload));
  }
}
