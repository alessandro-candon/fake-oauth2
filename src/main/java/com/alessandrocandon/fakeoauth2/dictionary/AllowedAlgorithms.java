package com.alessandrocandon.fakeoauth2.dictionary;

import java.util.Map;

public enum AllowedAlgorithms {
    RSA("RSA");

    private String algorithm;
    AllowedAlgorithms(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
