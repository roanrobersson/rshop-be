package br.com.roanrobersson.rshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.roanrobersson.rshop.dto.UriDTO;
import br.com.roanrobersson.rshop.services.FileService;

@RestController
@RequestMapping(value = "/files")
public class FileController {
	
	@Autowired
	private FileService service;
	
	@PostMapping
	public ResponseEntity<UriDTO> insert(@RequestParam("file") MultipartFile file) {
		UriDTO dto = service.insert(file);
		return ResponseEntity.ok().body(dto);
	}
	
}
