package br.com.roanrobersson.rshop.infrastructure.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockEmailService implements EmailService {

	private static Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(Message msg) {
		LOG.info("Sending email to: " + msg.getRecipients());
		LOG.info("Email sent!");
	}
}
