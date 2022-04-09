package br.com.roanrobersson.rshop.unit.mapper;

import static br.com.roanrobersson.rshop.builder.PrivilegeBuilder.aPrivilege;
import static br.com.roanrobersson.rshop.util.ResourceUtils.getContentFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.roanrobersson.rshop.api.v1.mapper.RoleMapper;
import br.com.roanrobersson.rshop.api.v1.model.RoleModel;
import br.com.roanrobersson.rshop.api.v1.model.input.RoleInput;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.service.PrivilegeService;

/*
 * Mappers tests should not use object builders from the package
 * br.com.roanrobersson.rshop.builder, 
 * because the builders themselves use Mappers in the object construction process.
 */

@ExtendWith(SpringExtension.class)
public class RoleMapperTests {

	@InjectMocks
	private RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

	@Mock
	private PrivilegeService privilegeService;

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final String JSON_ROLE = getContentFromResource("/json/correct/role.json");
	private static final String JSON_ROLE_INPUT = getContentFromResource("/json/correct/role-input.json");
	private static final String JSON_ROLE_INPUT_2 = getContentFromResource("/json/correct/role-input-2.json");
	private static final String JSON_ROLE_MODEL = getContentFromResource("/json/correct/role-model.json");

	@Test
	void toRoleModel_returCompatibleRoleModel() throws Exception {
		Role role = objectMapper.readValue(JSON_ROLE, Role.class);
		RoleModel expected = objectMapper.readValue(JSON_ROLE_MODEL, RoleModel.class);

		RoleModel result = roleMapper.toRoleModel(role);

		assertEquals(result, expected);
	}

	@Test
	void toRoleInput_returnCompatibleRoleInput() throws Exception {
		Role role = objectMapper.readValue(JSON_ROLE, Role.class);
		RoleInput expected = objectMapper.readValue(JSON_ROLE_INPUT, RoleInput.class);

		RoleInput result = roleMapper.toRoleInput(role);

		assertEquals(result, expected);
	}

	@Test
	void toRole_returnRole_RoleInputAsArgument() throws Exception {
		RoleInput input = objectMapper.readValue(JSON_ROLE_INPUT, RoleInput.class);
		UUID[] privilegesIds = input.getPrivileges().stream().map(p -> UUID.fromString(p.getId())).toArray(UUID[]::new);
		Privilege privilege1 = aPrivilege().withId(privilegesIds[0]).build();
		Privilege privilege2 = aPrivilege().withId(privilegesIds[1]).build();
		when(privilegeService.findById(privilege1.getId())).thenReturn(privilege1);
		when(privilegeService.findById(privilege2.getId())).thenReturn(privilege2);

		Role result = roleMapper.toRole(input);

		assertEquals(result.getName(), input.getName());
		assertTrue(result.getPrivileges().stream().anyMatch(c -> c.getId().equals(privilege1.getId())));
		assertTrue(result.getPrivileges().stream().anyMatch(c -> c.getId().equals(privilege2.getId())));
	}

	@Test
	void update_updateRole() throws Exception {
		RoleInput input = objectMapper.readValue(JSON_ROLE_INPUT_2, RoleInput.class);
		UUID privilegeId = UUID.fromString(input.getPrivileges().iterator().next().getId());
		Privilege privilege = aPrivilege().withId(privilegeId).build();
		Role result = objectMapper.readValue(JSON_ROLE, Role.class);
		when(privilegeService.findById(privilegeId)).thenReturn(privilege);

		roleMapper.update(input, result);

		assertEquals(input.getName(), result.getName());
		assertEquals(1, result.getPrivileges().size());
		assertTrue(result.getPrivileges().stream().anyMatch(c -> c.getId().equals(privilege.getId())));
	}
}
