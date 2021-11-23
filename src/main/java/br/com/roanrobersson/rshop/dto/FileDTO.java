package br.com.roanrobersson.rshop.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FileDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private MultipartFile file;
	private String description;
}
