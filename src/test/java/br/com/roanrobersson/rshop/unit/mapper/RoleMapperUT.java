package br.com.roanrobersson.rshop.unit.mapper;

import static br.com.roanrobersson.rshop.builder.PrivilegeBuilder.aPrivilege;
import static br.com.roanrobersson.rshop.util.ResourceUtils.getContentFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
public class RoleMapperUT {

	@InjectMocks
	private RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

	@Mock
	private PrivilegeService privilegeService;

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final String JSON_ROLE = getContentFromResource("/json/correct/role.json");
	private static final String JSON_ROLE_2 = getContentFromResource("/json/correct/role-2.json");
	private static final String JSON_ROLE_INPUT = getContentFromResource("/json/correct/role-input.json");
	private static final String JSON_ROLE_INPUT_2 = getContentFromResource("/json/correct/role-input-2.json");
	private static final String JSON_ROLE_MODEL = getContentFromResource("/json/correct/role-model.json");
	private static final String JSON_ROLE_MODEL_2 = getContentFromResource("/json/correct/role-model-2.json");

	@Test
	void toModel_returCompatibleRoleModel_RoleAsArgument() throws Exception {
		Role role = objectMapper.readValue(JSON_ROLE, Role.class);
		RoleModel expected = objectMapper.readValue(JSON_ROLE_MODEL, RoleModel.class);

		RoleModel result = roleMapper.toModel(role);

		assertEquals(result, expected);
	}

	@Test
	void toInput_ReturnCompatibleRoleInput_RoleAsArgument() throws Exception {
		Role role = objectMapper.readValue(JSON_ROLE, Role.class);
		RoleInput expected = objectMapper.readValue(JSON_ROLE_INPUT, RoleInput.class);

		RoleInput result = roleMapper.toInput(role);

		assertEquals(result, expected);
	}

	@Test
	void toRole_ReturnCompatibleRole_RoleInputAsArgument() throws Exception {
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
	void toModelPage_ReturnCompatibleProductModelPage_ProductPageAsArgument() throws Exception {
		Role role1 = objectMapper.readValue(JSON_ROLE, Role.class);
		Role role2 = objectMapper.readValue(JSON_ROLE_2, Role.class);
		long anyTotalItems = 500L;
		Pageable anyPageable = PageRequest.of(20, 15, Sort.by(Direction.ASC, "name"));
		Page<Role> input = new PageImpl<>(List.of(role1, role2), anyPageable, anyTotalItems);
		RoleModel expected1 = objectMapper.readValue(JSON_ROLE_MODEL, RoleModel.class);
		RoleModel expected2 = objectMapper.readValue(JSON_ROLE_MODEL_2, RoleModel.class);

		Page<RoleModel> actual = roleMapper.toModelPage(input);

		assertTrue(actual.stream().anyMatch((roleModel) -> roleModel.equals(expected1)));
		assertTrue(actual.stream().anyMatch((roleModel) -> roleModel.equals(expected2)));
		assertEquals(anyPageable.getPageNumber(), actual.getNumber());
		assertEquals(anyPageable.getPageSize(), actual.getSize());
		assertEquals(anyPageable.getSort(), actual.getSort());
		assertEquals(anyTotalItems, actual.getTotalElements());
	}

	@Test
	void toIdSet_ReturnCompatibleUUIDSet_RoleSetAsArgument() throws Exception {
		Role role1 = objectMapper.readValue(JSON_ROLE, Role.class);
		Role role2 = objectMapper.readValue(JSON_ROLE_2, Role.class);
		Set<Role> input = Set.of(role1, role2);

		Set<UUID> result = roleMapper.toIdSet(input);

		assertTrue(result.stream().anyMatch((uuid) -> uuid.equals(role1.getId())));
		assertTrue(result.stream().anyMatch((uuid) -> uuid.equals(role2.getId())));
	}

	@Test
	void update_CorrectUpdateRole_RoleInputAndRoleAsArgument() throws Exception {
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
