package br.com.roanrobersson.rshop.infrastructure.email.variables;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class AccountCreatedTemplateVariables {

	@NonNull
	private String name;

	@NonNull
	private String appUrl;

	public AccountCreatedTemplateVariables(String name, String appUrl) {
		this.name = name;
		this.appUrl = appUrl;
	}

	public String getName() {
		return name;
	}
}