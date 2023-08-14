package com.alessandrocandon.fakeoauth2.service;

import com.alessandrocandon.fakeoauth2.AppProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.jupiter.api.Assertions.*;

class RSAKeyServiceTest {

    AppProperties appProperties;

    RSAKeyService rsaKeyService;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        this.appProperties = new AppProperties(
                "RSA",
                "public_key.pem",
                "private_key_pkcs8.pem",
                "jwks.json"
        );
        this.rsaKeyService = new RSAKeyService(appProperties);
    }

    @Test
    void getPrivate() {
        var key = rsaKeyService.getPrivate();
        assertInstanceOf(RSAPrivateKey.class, key);
    }

    @Test
    void getPublic() {
        var key = rsaKeyService.getPublic();
        assertInstanceOf(RSAPublicKey.class, key);
    }
}