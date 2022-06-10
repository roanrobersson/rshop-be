package br.com.roanrobersson.rshop.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.roanrobersson.rshop.infrastructure.email.EmailService;
import br.com.roanrobersson.rshop.infrastructure.email.MockEmailService;
import br.com.roanrobersson.rshop.infrastructure.email.SendGridEmailService;

@Configuration
public class SendGridConfig {

	@Profile({ "dev", "prod" })
	@Bean
	public EmailService realEmailService() {
		return new SendGridEmailService();
	}

	@Profile({ "ut", "it" })
	@Bean
	public EmailService mockedEmailService() {
		return new MockEmailService();
	}

}
