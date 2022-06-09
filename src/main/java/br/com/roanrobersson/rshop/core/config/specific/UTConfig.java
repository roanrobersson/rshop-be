package br.com.roanrobersson.rshop.core.config.specific;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.roanrobersson.rshop.infrastructure.email.EmailService;
import br.com.roanrobersson.rshop.infrastructure.email.MockEmailService;

@Configuration
@Profile("ut")
public class UTConfig {

	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}