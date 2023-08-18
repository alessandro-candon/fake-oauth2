package com.alessandrocandon.fakeoauth2;

import com.alessandrocandon.fakeoauth2.dictionary.AllowedAlgorithm;
import com.alessandrocandon.fakeoauth2.service.IKeyService;
import com.alessandrocandon.fakeoauth2.service.RSAKeyService;
import com.alessandrocandon.fakeoauth2.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.NoSuchAlgorithmException;


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
    public UserService istantiateUserService() {
        return new UserService();
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

    @Bean(name = "fakeoauth2_ikeyservice")
    public IKeyService istantiateIKeyService() throws NoSuchAlgorithmException {
        switch (appProperties.algorithm()) {
            case AllowedAlgorithm.RSA:
                return new RSAKeyService(appProperties);
            default:
                throw new RuntimeException("This algorithm is not supported");
        }
    }
}
