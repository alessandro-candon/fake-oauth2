/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2;

import com.alessandrocandon.fakeoauth2.dictionary.AllowedAlgorithm;
import com.alessandrocandon.fakeoauth2.exception.NotSupportedConfigurationRuntimeException;
import com.alessandrocandon.fakeoauth2.service.IKeyService;
import com.alessandrocandon.fakeoauth2.service.RSAKeyService;
import java.security.NoSuchAlgorithmException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FakeOAuth2Application {

  private final AppProperties appProperties;

  public FakeOAuth2Application(AppProperties appProperties) {
    this.appProperties = appProperties;
  }

  public static void main(String[] args) {
    SpringApplication.run(FakeOAuth2Application.class, args);
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
      }
    };
  }

  @Bean
  public IKeyService istantiateIKeyService() throws NoSuchAlgorithmException {
    switch (appProperties.algorithm()) {
      case AllowedAlgorithm.RSA:
        return new RSAKeyService(appProperties);
      default:
        throw new NotSupportedConfigurationRuntimeException("This algorithm is not supported");
    }
  }
}
