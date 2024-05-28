/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.service;

import com.auth0.jwt.algorithms.Algorithm;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface IKeyService {
  public PrivateKey getPrivate();

  public PublicKey getPublic();

  public Algorithm getAlgorithm();
}
