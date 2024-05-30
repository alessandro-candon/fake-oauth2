package com.alessandrocandon.fakeoauth2.dto;

import java.util.ArrayList;

public record OpenIdConfigurationDto(String issuer,
                                     String authorization_endpoint,
                                     String token_endpoint,
                                     String userinfo_endpoint,
                                     String jwks_uri,
                                     ArrayList<String> scopes_supported) {}
