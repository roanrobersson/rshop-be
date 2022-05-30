package br.com.roanrobersson.rshop.api.v1.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.roanrobersson.rshop.api.v1.mapper.PrivilegeMapper;
import br.com.roanrobersson.rshop.api.v1.model.CountModel;
import br.com.roanrobersson.rshop.api.v1.model.PrivilegeModel;
import br.com.roanrobersson.rshop.api.v1.openapi.controller.PrivilegeControllerOpenApi;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.service.PrivilegeService;

@RestController
@RequestMapping(value = "/v1/privileges")
public class PrivilegeController implements PrivilegeControllerOpenApi {

	@Autowired
	private PrivilegeService service;

	@Autowired
	private PrivilegeMapper mapper;

	@GetMapping(produces = "application/json")
	@CheckSecurity.Role.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<PrivilegeModel>> list(@PageableDefault(size = 10) Pageable pageable) {
		Page<Privilege> privileges = service.list(pageable);
		Page<PrivilegeModel> privilegeModels = privileges.map(privilege -> mapper.toPrivilegeModel(privilege));
		return ResponseEntity.ok().body(privilegeModels);
	}

	@GetMapping(value = "/{privilegeId}", produces = "application/json")
	@CheckSecurity.Role.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<PrivilegeModel> findById(@PathVariable("privilegeId") UUID id) {
		Privilege privilege = service.findById(id);
		PrivilegeModel privilegeModel = mapper.toPrivilegeModel(privilege);
		return ResponseEntity.ok().body(privilegeModel);
	}

	@GetMapping(value = "/count", produces = "application/json")
	@CheckSecurity.Role.CanEdit
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CountModel> count() {
		CountModel countModel = new CountModel(service.count());
		return ResponseEntity.ok().body(countModel);
	}
}
