package com.alessandrocandon.fakeoauth2.controller;

import com.alessandrocandon.fakeoauth2.service.UserService;
import com.auth0.jwt.JWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json")
public class UserInfoController {

    @Autowired private UserService userService;

    @PostMapping("/idp/userinfo.openid")
    public void post(@RequestBody JsonNode rawUser) {
        userService.setUser(rawUser);
    }

    @GetMapping("/idp/userinfo.openid")
    public JsonNode get(@RequestHeader(HttpHeaders.AUTHORIZATION) String token)
            throws JsonProcessingException {
        String payload = JWT.decode(token).getPayload();
        return userService.getUserByJwtPayload(payload);
    }
}
