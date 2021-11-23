package br.com.roanrobersson.rshop.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UriDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String uri;
}
