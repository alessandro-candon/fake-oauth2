/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.controller;

import com.alessandrocandon.fakeoauth2.dto.JwtToken;
import com.alessandrocandon.fakeoauth2.service.JwtService;
import com.alessandrocandon.fakeoauth2.util.FileUtil;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class OAuthController {

  private final JwtService jwtService;

  public OAuthController(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @PostMapping(path = "/as/token.oauth2")
  public JwtToken token() {

    Map<String, Object> headers = Map.of("kid", "MAIN", "pi.atm", "5");

    return jwtService.getToken(headers);
  }

  @GetMapping(path = "/as/authorization.oauth2")
  public RedirectView redirect(
      @RequestParam("redirect_uri") String redirectUri, @RequestParam("state") String state) {
    var redirect =
        UriComponentsBuilder.fromUriString(redirectUri)
            .queryParam("state", state)
            .queryParam("code", "random_fake_code")
            .toUriString();
    return new RedirectView(redirect, false, true);
  }

  @GetMapping(path = "/ext/jwks")
  public String jwks() {
    return FileUtil.getResourceFileAsString("static/jwks.json");
  }
}
