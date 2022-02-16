package br.com.roanrobersson.rshop.infrastructure.email;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EmailService {

	void sendEmail(Message message);
	
	@Getter
	@Builder
	class Message {
		
		@Singular
		private Set<String> recipients;
		
		@NonNull
		private String subject;
		
		@NonNull
		private String body;
		
		@Singular("variable")
		private Map<String, Object> variables;
	}
}