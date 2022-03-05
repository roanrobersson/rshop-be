package br.com.roanrobersson.rshop.domain.service;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.roanrobersson.rshop.api.v1.model.input.ImageFileInput;

@Service
public class FileService {

	@Autowired
	private S3Service s3Service;

	public URL insertImage(ImageFileInput input) {
		MultipartFile file = input.getFile();
		return s3Service.uploadFile(file);
	}

}
