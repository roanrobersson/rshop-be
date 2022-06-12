package br.com.roanrobersson.rshop.api.v1.controller;

import java.net.URL;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.roanrobersson.rshop.api.v1.openapi.controller.FileControllerOpenApi;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.dto.input.ImageFileInput;
import br.com.roanrobersson.rshop.domain.dto.model.UriModel;
import br.com.roanrobersson.rshop.domain.service.FileService;

@RestController
@RequestMapping(value = "/v1/files")
public class FileController implements FileControllerOpenApi {

	@Autowired
	private FileService service;

	@PostMapping(value = "/images", produces = "application/json")
	@CheckSecurity.File.CanEdit
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<UriModel> insertImage(@Valid @ModelAttribute ImageFileInput imageFileInput) {
		URL url = service.insertImage(imageFileInput);
		UriModel uriDto = new UriModel(url.toString());
		return ResponseEntity.ok().body(uriDto);
	}
}
