package com.alessandrocandon.fakeoauth2.service;

import static org.junit.jupiter.api.Assertions.*;

import com.alessandrocandon.fakeoauth2.AppProperties;
import com.alessandrocandon.fakeoauth2.dto.JwtToken;
import com.alessandrocandon.fakeoauth2.util.FileUtil;
import com.auth0.jwk.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtServiceTest {

    private IKeyService rsaKeyService;

    static MockWebServer mockBackEnd;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException, IOException {
        var appProperties =
                new AppProperties(
                        "RSA",
                        "static/public_key.pem",
                        "static/private_key_pkcs8.pem",
                        "static/jwks.json");
        this.rsaKeyService = new RSAKeyService(appProperties);
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    void getTokenRsa() {
        Algorithm algorithm =
                Algorithm.RSA256(
                        (RSAPublicKey) rsaKeyService.getPublic(),
                        (RSAPrivateKey) rsaKeyService.getPrivate());

        Map<String, Object> headers = Map.of("kid", "MAIN", "pi.atm", "5");

        var jwtService = new JwtService(algorithm, headers);
        JwtToken jwtToken = jwtService.getToken();

        assertInstanceOf(JwtToken.class, jwtToken);
    }

    @Test
    void getTokenRsaAndVerifyWithJWKs() throws JwkException {

        mockBackEnd.enqueue(
                new MockResponse()
                        .setBody(FileUtil.getResourceFileAsString("static/jwks.json"))
                        .addHeader("Content-Type", "application/json"));

        Algorithm algorithm =
                Algorithm.RSA256(
                        (RSAPublicKey) rsaKeyService.getPublic(),
                        (RSAPrivateKey) rsaKeyService.getPrivate());

        Map<String, Object> headers = Map.of("kid", "MAIN", "pi.atm", "5");

        var jwtService = new JwtService(algorithm, headers);
        JwtToken jwtToken = jwtService.getToken();

        DecodedJWT jwt = JWT.decode(jwtToken.accessToken());
        JwkProvider provider = new UrlJwkProvider(mockBackEnd.url("/").toString());
        Jwk jwk = provider.get(jwt.getKeyId());
        Algorithm algorithmOfJwk = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);

        algorithmOfJwk.verify(jwt);
    }
}
