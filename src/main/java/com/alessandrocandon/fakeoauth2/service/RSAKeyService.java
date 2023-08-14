package com.alessandrocandon.fakeoauth2.service;

import com.alessandrocandon.fakeoauth2.AppProperties;
import com.alessandrocandon.fakeoauth2.util.FileUtil;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class RSAKeyService implements IKeyService {

    private final String publicKeyClean;
    private final String privateKeyClean;

    private final KeyFactory kf;

    public RSAKeyService(AppProperties appProperties) throws NoSuchAlgorithmException {
        String privateKeyRaw = FileUtil.getResourceFileAsString(appProperties.privateKeyPath());
        String publicKeyRaw = FileUtil.getResourceFileAsString(appProperties.publicKeyPath());
        privateKeyClean = cleaner(privateKeyRaw);
        publicKeyClean = cleaner(publicKeyRaw);
        kf = KeyFactory.getInstance("RSA");
    }

    public RSAPrivateKey getPrivate() {
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyClean));
        try {
            return (RSAPrivateKey) kf.generatePrivate(keySpecPKCS8);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public RSAPublicKey getPublic() {
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyClean));
        try {
            return (RSAPublicKey) kf.generatePublic(keySpecX509);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private String cleaner(String rawKey) {
        return rawKey
                .replaceAll("\\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "");
    }
}
