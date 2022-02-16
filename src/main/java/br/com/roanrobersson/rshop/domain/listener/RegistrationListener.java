package br.com.roanrobersson.rshop.domain.listener;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import br.com.roanrobersson.rshop.domain.event.OnRegistrationCompleteEvent;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.infrastructure.email.EmailService;
import br.com.roanrobersson.rshop.infrastructure.email.variables.AccountCreatedTemplateVariables;

@Component
public class RegistrationListener {

	@Autowired
	private EmailService emailService;

	@Autowired
	private HttpServletRequest request;

	@TransactionalEventListener
	private void sendAccountCreatedEmail(OnRegistrationCompleteEvent event) {
		User user = event.getUser();
		String appUrl = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
		AccountCreatedTemplateVariables variables = new AccountCreatedTemplateVariables(user.getName(), appUrl);
		var msg = EmailService.Message.builder()
				.subject("Welcome to rShop!")
				.body("emails/account-created.html")
				.variable("variables", variables)
				.recipient(user.getEmail())
				.build();
		emailService.sendEmail(msg);
	}
}