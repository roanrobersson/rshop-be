package br.com.roanrobersson.rshop.infrastructure.email;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class SendGridEmailService implements EmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SendGridEmailService.class);

	@Autowired
	private SendGrid sendGrid;

	@Value("${spring.sendgrid.api-key}")
	private String key;

	@Value("${rshop.mail.sender.name}")
	private String senderName;

	@Value("${rshop.mail.sender.email}")
	private String senderEmail;

	@Autowired
	private Configuration freemarkerConfig;

	public void sendEmail(Message msg) {
		String body = processTemplate(msg);
		Email fromEmail = new Email(senderEmail, senderName);
		Content content = new Content("text/html", body);
		String subject = msg.getSubject();
		for (String recipient : msg.getRecipients()) {
			Email toEmail = new Email(recipient);
			Mail mail = new Mail(fromEmail, subject, toEmail, content);
			sendRequest(mail);
		}
	}

	protected void sendRequest(Mail mail) {
		try {
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			LOGGER.info("Sending email to: {}", mail.getSubject());
			Response response = sendGrid.api(request);
			if (response.getStatusCode() >= 400 && response.getStatusCode() <= 500) {
				LOGGER.error("Error sending email: {}", response.getBody());
				throw new EmailException(response.getBody());
			} else {
				LOGGER.info("Email sent! Status = {}", response.getStatusCode());
			}
		} catch (IOException e) {
			throw new EmailException(e.getMessage());
		}
	}

	protected String processTemplate(Message msg) {
		try {
			Template template = freemarkerConfig.getTemplate(msg.getBody());
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, msg.getVariables());
		} catch (Exception e) {
			throw new EmailException("Unable to mount the email template", e);
		}
	}
}
