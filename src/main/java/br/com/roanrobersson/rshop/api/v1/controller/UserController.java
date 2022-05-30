package br.com.roanrobersson.rshop.api.v1.controller;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.roanrobersson.rshop.api.v1.mapper.UserMapper;
import br.com.roanrobersson.rshop.api.v1.model.CountModel;
import br.com.roanrobersson.rshop.api.v1.model.UserModel;
import br.com.roanrobersson.rshop.api.v1.model.input.UserChangePasswordInput;
import br.com.roanrobersson.rshop.api.v1.model.input.UserInsert;
import br.com.roanrobersson.rshop.api.v1.model.input.UserUpdate;
import br.com.roanrobersson.rshop.api.v1.openapi.controller.UserControllerOpenApi;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.service.UserService;

@RestController
@RequestMapping(value = "/v1/users")
public class UserController implements UserControllerOpenApi {

	@Autowired
	private UserService service;

	@Autowired
	private UserMapper mapper;

	@GetMapping(produces = "application/json")
	@CheckSecurity.User.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<UserModel>> list(@PageableDefault(size = 10) Pageable pageable) {
		Page<User> userPage = service.list(pageable);
		Page<UserModel> userModels = userPage.map(x -> mapper.toUserModel(x));
		return ResponseEntity.ok(userModels);
	}

	@GetMapping(value = "/{userId}", produces = "application/json")
	@CheckSecurity.User.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<UserModel> findById(@PathVariable UUID userId) {
		User user = service.findById(userId);
		UserModel userModel = mapper.toUserModel(user);
		return ResponseEntity.ok(userModel);
	}

	@PostMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<UserModel> insert(@Valid @RequestBody UserInsert userInsert) {
		User user = service.insert(userInsert);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(user.getId()).toUri();
		UserModel userModel = mapper.toUserModel(user);
		return ResponseEntity.created(uri).body(userModel);
	}

	@PatchMapping(value = "/{userId}", produces = "application/json")
	@CheckSecurity.User.CanEdit
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<UserModel> update(@PathVariable UUID userId, @Valid @RequestBody UserUpdate userUpdate) {
		User user = service.update(userId, userUpdate);
		UserModel userModel = mapper.toUserModel(user);
		return ResponseEntity.ok(userModel);
	}

	@DeleteMapping(value = "/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.User.CanEdit
	public ResponseEntity<Void> delete(@PathVariable UUID userId) {
		service.delete(userId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/count", produces = "application/json")
	@CheckSecurity.User.CanEdit
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CountModel> count() {
		CountModel countModel = new CountModel(service.count());
		return ResponseEntity.ok().body(countModel);
	}

	@PutMapping(value = "/{userId}/password")
	@CheckSecurity.User.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> changePassword(@PathVariable UUID userId,
			@Valid @RequestBody UserChangePasswordInput userChangePasswordInput) {
		service.changePassword(userId, userChangePasswordInput);
		return ResponseEntity.noContent().build();
	}
}