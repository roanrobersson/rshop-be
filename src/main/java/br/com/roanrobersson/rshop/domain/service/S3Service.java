package br.com.roanrobersson.rshop.domain.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class S3Service {

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	public URL uploadFile(MultipartFile file) {
		try {
			//String originalName = file.getOriginalFilename();
			//String extension = FilenameUtils.getExtension(originalName);
			String fileName = UUID.randomUUID().toString().replace("-", "");
			InputStream is = file.getInputStream();
			String contentType = file.getContentType();
			Long contentLength = file.getSize();
			return uploadFile(is, fileName, contentType, contentLength);
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	private URL uploadFile(InputStream is, String fileName, String contentType, Long contentLength) {
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentType(contentType);
		meta.setContentLength(contentLength);
		s3client.putObject(bucketName, fileName, is, meta);
		return s3client.getUrl(bucketName, fileName);
	}
}