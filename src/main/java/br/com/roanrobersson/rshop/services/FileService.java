package br.com.roanrobersson.rshop.services;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.roanrobersson.rshop.dto.UriDTO;

@Service
public class FileService {
	
	@Autowired
	private S3Service s3Service;
	
	public UriDTO insert(MultipartFile file) {
		URL url = s3Service.uploadFile(file);
		return new UriDTO(url.toString());
	}
	
}
