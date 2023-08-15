package com.alessandrocandon.fakeoauth2.service;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface IKeyService {
    public PrivateKey getPrivate();

    public PublicKey getPublic();
}
