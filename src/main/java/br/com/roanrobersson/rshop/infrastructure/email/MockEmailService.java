package br.com.roanrobersson.rshop.infrastructure.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockEmailService implements EmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(Message msg) {
		LOGGER.info("Sending email to: {}", msg.getRecipients());
		LOGGER.info("Email sent!");
	}
}
