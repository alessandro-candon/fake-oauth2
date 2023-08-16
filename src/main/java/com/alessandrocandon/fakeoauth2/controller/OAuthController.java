package com.alessandrocandon.fakeoauth2.controller;

import com.alessandrocandon.fakeoauth2.dto.JwtToken;
import com.alessandrocandon.fakeoauth2.service.JwtService;
import com.alessandrocandon.fakeoauth2.service.RSAKeyService;
import com.alessandrocandon.fakeoauth2.service.UserService;
import com.alessandrocandon.fakeoauth2.util.FileUtil;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(produces = "application/json")
public class OAuthController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(OAuthController.class);

    @Autowired private RSAKeyService rsaKeyService;

    @Autowired private UserService userService;

    @Autowired private JwtService jwtService;

    @GetMapping(path = "/as/token.oauth2")
    public JwtToken token() {

        // TODO:  maybe move this on RSAKey Service
        Algorithm algorithm =
                Algorithm.RSA256(
                        (RSAPublicKey) rsaKeyService.getPublic(),
                        (RSAPrivateKey) rsaKeyService.getPrivate());

        Map<String, Object> headers = Map.of("kid", "MAIN", "pi.atm", "5");

        return jwtService.getToken(algorithm, headers);
    }

    @GetMapping(path = "/as/authorization.oauth2")
    public RedirectView redirect(@RequestParam String redirectUri, @RequestParam String state) {
        var redirect =
                UriComponentsBuilder.fromUriString(redirectUri)
                        .queryParam("state", state)
                        .queryParam("code", "random_fake_code")
                        .toUriString();
        return new RedirectView(redirect, false, true);
    }

    @PostMapping(path = "/as/token.oauth2/payload")
    public void tokenPayload(@RequestBody JsonNode rawJwtPayload) {
        userService.setJwtPayload(rawJwtPayload);
    }

    @GetMapping(path = "/ext/jwks")
    public String jwks() {
        return FileUtil.getResourceFileAsString("static/jwks.json");
    }
}
