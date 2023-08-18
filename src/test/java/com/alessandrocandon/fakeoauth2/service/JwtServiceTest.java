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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtServiceTest {

    private IKeyService rsaKeyService;

    static MockWebServer mockBackEnd;

    @Autowired private JwtService jwtService;

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

        JwtToken jwtToken = jwtService.getToken(algorithm, headers);

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

        JwtToken jwtToken = jwtService.getToken(algorithm, headers);

        DecodedJWT jwt = JWT.decode(jwtToken.accessToken());
        JwkProvider provider = new UrlJwkProvider(mockBackEnd.url("/").toString());
        Jwk jwk = provider.get(jwt.getKeyId());
        Algorithm algorithmOfJwk = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);

        algorithmOfJwk.verify(jwt);
    }

    @Test
    void getDecodedPayload() {
        var tokenPayloadString =
                jwtService.getDecodedPayload(
                        "Bearer"
                            + " eyJraWQiOiJNQUlOIiwicGkuYXRtIjoiNSIsImFsZyI6IlJTMjU2IiwidHlwIjoiSldUIn0.eyJzY29wZSI6WyJvcGVuaWQiLCJwcm9maWxlIl0sImF1dGhvcml6YXRpb25fZGV0YWlscyI6W10sImNsaWVudF9pZCI6Im15Y2lkIiwiaXNzIjoiZXhhbXBsZS5vcmciLCJqdGkiOiIxMjMiLCJzdWIiOiJhbGVzc2FuZHJvLWNhbmRvbiIsInVpZCI6ImFsZXNzYW5kcm8tY2FuZG9uIiwib3JpZ2luIjoiY29ycG9yYXRlIiwiaWF0IjoxNjkyMDI1MDU5LCJ1dWlkIjoieHh4eHh4eC00N2EzLTM0NGYtYmJiNC14eHh4eHgiLCJ1c2VyX2tleV9pZCI6MH0.rrK6SHgWzlQFAS4WAP9jnSfzmvWveCXYsiiRllyURkxqwC08Dxjf7oSGY8bZvSlCjOqMc7eCZZE9R5atQBy3RYcpKAWS4kCXatJCAvnxjEtYLH0RyzoGFQrHkhtX-_sH-Zu-7FPogjcBl5Z0jiBQ46HOHPJn8TYwNnona68xabnaeDzGwCz2Fpf34UBdzIkAdDLVFMA09pP80FAT8962u2OyyDPYpldLagIHGlfLG9NtX8NGBgpUAkPkC0r6Y8KOBdK4x2C3cyBORg9BrisfdamhCO1qRqzGWfO6nK1N6gAK4jLxEnL72oMyqM2nOUa-EsQ6skASk2G5phCpjEavUw");
        assertTrue(tokenPayloadString.contains("client_id"));
    }
}
