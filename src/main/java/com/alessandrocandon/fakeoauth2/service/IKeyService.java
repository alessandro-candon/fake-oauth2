package com.alessandrocandon.fakeoauth2.service;

import com.alessandrocandon.fakeoauth2.AppProperties;
import com.alessandrocandon.fakeoauth2.util.FileUtil;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public interface IKeyService {
    public PrivateKey getPrivate();

    public PublicKey getPublic();
}
