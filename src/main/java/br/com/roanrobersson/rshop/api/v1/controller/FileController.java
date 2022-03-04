package br.com.roanrobersson.rshop.api.v1.controller;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.roanrobersson.rshop.api.v1.model.UriModel;
import br.com.roanrobersson.rshop.api.v1.openapi.controller.FileControllerOpenApi;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.service.FileService;

@RestController
@RequestMapping(value = "/v1/files")
public class FileController implements FileControllerOpenApi {

	@Autowired
	private FileService service;

	@PostMapping
	@CheckSecurity.File.CanEdit
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<UriModel> insertImage(@RequestParam("file") MultipartFile file) {
		URL url = service.insertImage(file);
		UriModel uriDto = new UriModel(url.toString());
		return ResponseEntity.ok().body(uriDto);
	}
}
