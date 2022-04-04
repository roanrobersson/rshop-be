package br.com.roanrobersson.rshop.util;

import lombok.Getter;

@Getter
public enum Account {

	ADMINSTRATOR("administrator@gmail.com", "12345678"), OPERATOR("operator@gmail.com", "12345678"),
	CLIENT("client@gmail.com", "12345678"), NOT_VERIFIED("notverified@gmail.com", "12345678");

	private String userName;
	private String password;

	Account(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
}
