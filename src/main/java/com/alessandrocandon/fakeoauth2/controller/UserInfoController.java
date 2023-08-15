package com.alessandrocandon.fakeoauth2.controller;

import com.alessandrocandon.fakeoauth2.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
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
    public JsonNode get() {
        return userService.getUser();
    }
}
