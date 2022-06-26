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

		@Singular(ignoreNullCollections = true)
		private Set<String> recipients;

		@NonNull
		private String subject;

		@NonNull
		private String body;

		@Singular(ignoreNullCollections = true, value = "variable")
		private Map<String, Object> variables;
	}
}