package com.alessandrocandon.fakeoauth2.util;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FileUtilTest {

    @Test
    public void getResourceFileAsString() {
        String content = FileUtil.getResourceFileAsString("static/public_key.pem");
        assertTrue(content.contains("MIICIjANBgkqhkiG9w0BAQEFAAOCAg"));
    }
}