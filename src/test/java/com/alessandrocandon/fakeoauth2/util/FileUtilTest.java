package com.alessandrocandon.fakeoauth2.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FileUtilTest {

    @Test
    public void getResourceFileAsString() {
        String content = FileUtil.getResourceFileAsString("static/public_key.pem");
        assertTrue(
                content.contains(
                        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtHdbap68Sj6F9BqEaNhR"));
    }
}
