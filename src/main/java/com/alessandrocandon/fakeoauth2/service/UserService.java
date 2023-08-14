package com.alessandrocandon.fakeoauth2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.util.Map;

public class UserService {

    private static JsonNode jwtPayload;
    private static JsonNode user;

    private static Map<String, JsonNode> userByJwt;

    public UserService() {
        user = JsonNodeFactory.instance.objectNode();
    }
    public JsonNode getUser() {
        return user;
    }

    public void setUser(JsonNode user) {
        UserService.user = user;
    }

    public JsonNode getJwtPayload() {
        return jwtPayload;
    }

    public void setJwtPayload(JsonNode jwtPayload) {
        UserService.jwtPayload = jwtPayload;
    }
}
