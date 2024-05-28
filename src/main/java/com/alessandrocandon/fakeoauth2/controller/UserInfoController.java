/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.controller;

import com.alessandrocandon.fakeoauth2.service.JwtService;
import com.alessandrocandon.fakeoauth2.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {

  private final JwtService jwtService;

  private final UserService userService;

  public UserInfoController(UserService userService, JwtService jwtService) {
    this.jwtService = jwtService;
    this.userService = userService;
  }

  @PostMapping("/idp/userinfo.openid")
  public JsonNode get(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token)
      throws JsonProcessingException {
    var jwtPayload = jwtService.getDecodedPayload(token);
    return userService.getUserByJwtPayload(jwtPayload);
  }
}
