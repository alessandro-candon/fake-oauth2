package com.alessandrocandon.fakeoauth2.controller;

import com.alessandrocandon.fakeoauth2.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UtilController {

    @Autowired private UserService userService;

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
