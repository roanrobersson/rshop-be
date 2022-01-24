package br.com.roanrobersson.rshop.domain.service;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	@Autowired
	private S3Service s3Service;

	public URL insert(MultipartFile file) {
		return s3Service.uploadFile(file);
	}

}
