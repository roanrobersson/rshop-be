package br.com.roanrobersson.rshop.unit.mapper;

import static br.com.roanrobersson.rshop.util.ResourceUtils.getContentFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.roanrobersson.rshop.api.v1.mapper.UserMapper;
import br.com.roanrobersson.rshop.api.v1.model.UserModel;
import br.com.roanrobersson.rshop.api.v1.model.input.UserInsert;
import br.com.roanrobersson.rshop.api.v1.model.input.UserUpdate;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.service.RoleService;

/*
 * Mappers tests should not use object builders from the package
 * br.com.roanrobersson.rshop.builder, 
 * because the builders themselves use Mappers in the object construction process.
 */

@ExtendWith(SpringExtension.class)
public class UserMapperTests {

	@InjectMocks
	private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

	@Mock
	private RoleService roleService;

	private static ObjectMapper objectMapper = new ObjectMapper();

	private static final String JSON_USER = getContentFromResource("/json/correct/user.json");
	private static final String JSON_USER_2 = getContentFromResource("/json/correct/user-2.json");
	private static final String JSON_USER_INSERT = getContentFromResource("/json/correct/user-insert.json");
	private static final String JSON_USER_UPDATE = getContentFromResource("/json/correct/user-update.json");
	private static final String JSON_USER_UPDATE_2 = getContentFromResource("/json/correct/user-update-2.json");
	private static final String JSON_USER_MODEL = getContentFromResource("/json/correct/user-model.json");

	@BeforeAll
	static void setup() {
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	@Test
	void toUserModel_returCompatibleUserModel() throws Exception {
		User user = objectMapper.readValue(JSON_USER, User.class);
		UserModel expected = objectMapper.readValue(JSON_USER_MODEL, UserModel.class);

		UserModel result = userMapper.toUserModel(user);

		assertEquals(result, expected);
	}

	@Test
	void toUserInsert_returnCompatibleUserInsert() throws Exception {
		User user = objectMapper.readValue(JSON_USER, User.class);
		user.setPassword("12345678");
		UserInsert expected = objectMapper.readValue(JSON_USER_INSERT, UserInsert.class);

		UserInsert result = userMapper.toUserInsert(user);

		assertEquals(result, expected);
	}

	@Test
	void toUserUpdate_returnCompatibleUserUpdate() throws Exception {
		User user = objectMapper.readValue(JSON_USER, User.class);
		UserUpdate expected = objectMapper.readValue(JSON_USER_UPDATE, UserUpdate.class);

		UserUpdate result = userMapper.toUserUpdate(user);

		assertEquals(result, expected);
	}

	@Test
	void toUser_returnUser_UserInsertAsArgument() throws Exception {
		UserInsert input = objectMapper.readValue(JSON_USER_INSERT, UserInsert.class);
		User expected = objectMapper.readValue(JSON_USER, User.class);
		expected.setId(null);
		expected.getAddresses().clear();
		expected.getRoles().clear();

		User result = userMapper.toUser(input);

		assertEquals(result, expected);
	}

	@Test
	void update_updateUser() throws Exception {
		UserUpdate input = objectMapper.readValue(JSON_USER_UPDATE_2, UserUpdate.class);
		User result = objectMapper.readValue(JSON_USER, User.class);
		User expected = objectMapper.readValue(JSON_USER_2, User.class);

		userMapper.update(input, result);

		assertEquals(result, expected);
	}
}
