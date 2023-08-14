package com.alessandrocandon.fakeoauth2.controller;

import com.alessandrocandon.fakeoauth2.dto.JwtToken;
import com.alessandrocandon.fakeoauth2.service.IKeyService;
import com.alessandrocandon.fakeoauth2.service.JwtService;
import com.alessandrocandon.fakeoauth2.service.UserService;
import com.alessandrocandon.fakeoauth2.util.FileUtil;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;


@RestController
@RequestMapping(produces = "application/json")
public class OAuthController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(OAuthController.class);

    @Autowired
    private IKeyService rsaKeyService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/as/token.oauth2")
    public JwtToken token() {

        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) rsaKeyService.getPublic(), (RSAPrivateKey) rsaKeyService.getPrivate());

        Map<String, Object> headers = Map.of("kid", "MAIN", "pi.atm", "5");

        var jwtService = new JwtService(algorithm, headers, userService.getJwtPayload());

        return jwtService.getToken();
    }

    @PostMapping(path = "/as/token.oauth2/payload")
    public void tokenPayload(@RequestBody JsonNode rawJwtPayload) {
        userService.setJwtPayload(rawJwtPayload);
    }


    @GetMapping(path = "/ext/JWKS")
    public String jwks() {
        return FileUtil.getResourceFileAsString("static/jwks.json");
    }
}
