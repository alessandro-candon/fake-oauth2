package com.alessandrocandon.fakeoauth2;

import com.alessandrocandon.fakeoauth2.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

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
	public UserService get() {
		return new UserService();
	}

}
