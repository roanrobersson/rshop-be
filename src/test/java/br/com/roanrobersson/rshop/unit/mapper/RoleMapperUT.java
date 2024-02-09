package br.com.roanrobersson.rshop.unit.mapper;

import static br.com.roanrobersson.rshop.domain.model.Privilege.aPrivilege;
import static br.com.roanrobersson.rshop.util.ResourceUtils.getContentFromResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

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
import com.fasterxml.jackson.databind.json.JsonMapper;

import br.com.roanrobersson.rshop.domain.dto.input.RoleInput;
import br.com.roanrobersson.rshop.domain.dto.model.RoleModel;
import br.com.roanrobersson.rshop.domain.mapper.RoleMapper;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.service.PrivilegeService;

/*
 * Mappers tests should not use object builders from the package
 * br.com.roanrobersson.rshop.builder, 
 * because the builders themselves use Mappers in the object construction process.
 */

@ExtendWith(SpringExtension.class)
class RoleMapperUT {

	@InjectMocks
	private RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

	@Mock
	private PrivilegeService privilegeService;

	private ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

	private static final String JSON_ROLE = getContentFromResource("/json/correct/role.json");
	private static final String JSON_ROLE_2 = getContentFromResource("/json/correct/role-2.json");
	private static final String JSON_ROLE_INPUT = getContentFromResource("/json/correct/role-input.json");
	private static final String JSON_ROLE_INPUT_2 = getContentFromResource("/json/correct/role-input-2.json");
	private static final String JSON_ROLE_MODEL = getContentFromResource("/json/correct/role-model.json");
	private static final String JSON_ROLE_MODEL_2 = getContentFromResource("/json/correct/role-model-2.json");

	@Test
	void toModel_returnCompatibleRoleModel_RoleAsArgument() throws Exception {
		Role role = objectMapper.readValue(JSON_ROLE, Role.class);
		RoleModel expected = objectMapper.readValue(JSON_ROLE_MODEL, RoleModel.class);

		RoleModel actual = roleMapper.toModel(role);

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void toInput_ReturnCompatibleRoleInput_RoleAsArgument() throws Exception {
		Role role = objectMapper.readValue(JSON_ROLE, Role.class);
		RoleInput expected = objectMapper.readValue(JSON_ROLE_INPUT, RoleInput.class);

		RoleInput actual = roleMapper.toInput(role);

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void toRole_ReturnCompatibleRole_RoleInputAsArgument() throws Exception {
		RoleInput input = objectMapper.readValue(JSON_ROLE_INPUT, RoleInput.class);
		Role expected = objectMapper.readValue(JSON_ROLE, Role.class);
		Long[] privilegesIds = input.getPrivileges().stream().map(p -> p.getId()).toArray(Long[]::new);
		Privilege privilege1 = aPrivilege().id(privilegesIds[0]).build();
		Privilege privilege2 = aPrivilege().id(privilegesIds[1]).build();
		when(privilegeService.findById(privilege1.getId())).thenReturn(privilege1);
		when(privilegeService.findById(privilege2.getId())).thenReturn(privilege2);

		Role actual = roleMapper.toRole(input);

		assertThat(actual)
				.usingRecursiveComparison()
				.ignoringFields("id", "privileges", "createdAt", "updatedAt")
				.isEqualTo(expected);
		assertThat(actual.getPrivileges()).containsExactlyInAnyOrderElementsOf(expected.getPrivileges());
	}

	@Test
	void toModelPage_ReturnCompatibleProductModelPage_ProductPageAsArgument() throws Exception {
		long anyTotalItems = 500L;
		int anyPage = 20;
		int anySize = 15;
		Sort anySort = Sort.by(Direction.ASC, "any");
		Pageable anyPageable = PageRequest.of(anyPage, anySize, anySort);
		Role role1 = objectMapper.readValue(JSON_ROLE, Role.class);
		Role role2 = objectMapper.readValue(JSON_ROLE_2, Role.class);
		Page<Role> input = new PageImpl<>(List.of(role1, role2), anyPageable, anyTotalItems);
		RoleModel expectedRoleModel1 = objectMapper.readValue(JSON_ROLE_MODEL, RoleModel.class);
		RoleModel expectedRoleModel2 = objectMapper.readValue(JSON_ROLE_MODEL_2, RoleModel.class);

		Page<RoleModel> actual = roleMapper.toModelPage(input);

		assertThat(actual.getNumber()).isEqualTo(anyPage);
		assertThat(actual.getSize()).isEqualTo(anySize);
		assertThat(actual.getSort()).usingRecursiveComparison().isEqualTo(anySort);
		assertThat(actual.getTotalElements()).isEqualTo(anyTotalItems);
		assertThat(actual.getContent()).containsExactlyInAnyOrder(expectedRoleModel1, expectedRoleModel2);
	}

	@Test
	void update_CorrectUpdateRole_RoleInputAndRoleAsArgument() throws Exception {
		RoleInput input = objectMapper.readValue(JSON_ROLE_INPUT_2, RoleInput.class);
		Long privilegeId = input.getPrivileges().iterator().next().getId();
		Privilege privilege = aPrivilege().id(privilegeId).build();
		Role actual = objectMapper.readValue(JSON_ROLE, Role.class);
		when(privilegeService.findById(privilegeId)).thenReturn(privilege);

		roleMapper.update(input, actual);

		assertThat(actual)
				.usingRecursiveComparison()
				.ignoringFields("id", "privileges", "createdAt", "updatedAt")
				.isEqualTo(input);
		assertThat(actual.getPrivileges()).containsExactly(privilege);
	}
}
