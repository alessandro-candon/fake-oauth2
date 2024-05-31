/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.controller;

import com.alessandrocandon.fakeoauth2.service.JwtService;
import com.alessandrocandon.fakeoauth2.service.SetUpConfigurationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {

  private final JwtService jwtService;

  public UserInfoController(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @RequestMapping(value = "/idp/userinfo.openid", method = {RequestMethod.GET, RequestMethod.POST})
  public JsonNode get(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token)
      throws JsonProcessingException {
    var jwtPayload = jwtService.getDecodedPayload(token);
    var setUpConfiguration = SetUpConfigurationService.getConfigurationByJwtPayload(jwtPayload);
    return setUpConfiguration.user();
  }
}
