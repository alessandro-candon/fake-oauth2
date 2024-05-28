/* Decathlon Italy - Tacos Team(C) 2023 */
package com.alessandrocandon.fakeoauth2;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
public record AppProperties(
    String algorithm, String hash, String publicKeyPath, String privateKeyPath, String jwksPath) {}
