package br.com.roanrobersson.rshop.dto;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

public class FileDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private MultipartFile file;
	private String description;
	
	public FileDTO() {
	}
	
	public FileDTO(MultipartFile file, String description) {
		this.file = file;
		this.description = description;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
