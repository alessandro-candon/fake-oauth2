/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.service;

import com.auth0.jwt.algorithms.Algorithm;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface IKeyService {
  PrivateKey getPrivate();

  PublicKey getPublic();

  Algorithm getAlgorithm();
}
