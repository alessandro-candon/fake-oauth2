/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.controller;

import com.alessandrocandon.fakeoauth2.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
public class UtilController {

  private UserService userService;

  public UtilController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/userinfo")
  public void post(@RequestBody JsonNode rawUser) {
    userService.setUser(rawUser);
  }

  @GetMapping("/userinfo")
  public Map<Integer, JsonNode> getAll() {
    return userService.getAllUsers();
  }

  @DeleteMapping("/userinfo")
  public void resetUsers() {
    userService.initUsers();
  }

  @PostMapping(path = "/as/token.oauth2/payload")
  public void tokenPayload(@RequestBody JsonNode rawJwtPayload) {
    userService.setJwtPayload(rawJwtPayload);
  }

  @DeleteMapping("/as/token.oauth2/payload")
  public void resetJwt() {
    userService.initJwtPayload();
  }
}
