package br.com.roanrobersson.rshop.api.v1.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.roanrobersson.rshop.api.v1.mapper.PrivilegeMapper;
import br.com.roanrobersson.rshop.api.v1.model.PrivilegeModel;
import br.com.roanrobersson.rshop.api.v1.openapi.controller.PrivilegeControllerOpenApi;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.service.PrivilegeService;

@RestController
@RequestMapping(value = "/v1/privileges")
public class PrivilegeController implements PrivilegeControllerOpenApi{

	@Autowired
	private PrivilegeService service;

	@Autowired
	private PrivilegeMapper mapper;
	
	@GetMapping(produces = "application/json")
	@CheckSecurity.Role.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<PrivilegeModel>> getPrivileges(
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		Sort sort = Sort.by(new Order(Direction.fromString(direction), orderBy));
		List<Privilege> privileges = service.findAll(sort);
		List<PrivilegeModel> privilegeModels = privileges.stream()
				.map(privilege -> mapper.toPrivilegeModel(privilege)).collect(Collectors.toList());
		return ResponseEntity.ok().body(privilegeModels);
	}
}
