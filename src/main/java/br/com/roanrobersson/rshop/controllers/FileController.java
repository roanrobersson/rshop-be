package br.com.roanrobersson.rshop.controllers;

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

import br.com.roanrobersson.rshop.config.CheckSecurity;
import br.com.roanrobersson.rshop.dto.UriDTO;
import br.com.roanrobersson.rshop.services.FileService;

@RestController
@RequestMapping(value = "/files")
public class FileController {

	@Autowired
	private FileService service;

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@CheckSecurity.File.CanEdit
	public ResponseEntity<UriDTO> insert(@RequestParam("file") MultipartFile file) {
		URL url = service.insert(file);
		UriDTO uriDto = new UriDTO(url.toString());
		return ResponseEntity.ok().body(uriDto);
	}

}
