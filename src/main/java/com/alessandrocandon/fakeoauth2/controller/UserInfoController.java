package com.alessandrocandon.fakeoauth2.controller;

import com.alessandrocandon.fakeoauth2.service.JwtService;
import com.alessandrocandon.fakeoauth2.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {

    @Autowired private UserService userService;
    @Autowired private JwtService jwtService;

    @PostMapping("/idp/userinfo.openid")
    public void post(@RequestBody JsonNode rawUser) {
        userService.setUser(rawUser);
    }

    @GetMapping("/idp/userinfo.openid")
    public JsonNode get(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token)
            throws JsonProcessingException {
        var jwtPayload = jwtService.getDecodedPayload(token);
        return userService.getUserByJwtPayload(jwtPayload);
    }
}
