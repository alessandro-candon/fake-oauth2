package com.alessandrocandon.fakeoauth2.service;

import com.alessandrocandon.fakeoauth2.AppProperties;
import com.alessandrocandon.fakeoauth2.dto.JwtToken;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private IKeyService rsaKeyService;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        var appProperties = new AppProperties(
                "RSA",
                "public_key.pem",
                "private_key_pkcs8.pem",
                "jwks.json"
        );
        this.rsaKeyService = new RSAKeyService(appProperties);
    }

    @Test
    void getTokenRsa() {
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) rsaKeyService.getPublic(), (RSAPrivateKey) rsaKeyService.getPrivate());

        Map<String, Object> headers = Map.of("kid","MAIN", "pi.atm", "5");

        var jwtService = new JwtService(algorithm, headers);
        JwtToken jwtToken = jwtService.getToken();

        assertInstanceOf(JwtToken.class, jwtToken);
    }
}