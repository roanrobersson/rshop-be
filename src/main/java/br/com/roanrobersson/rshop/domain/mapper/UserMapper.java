package br.com.roanrobersson.rshop.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import br.com.roanrobersson.rshop.core.config.MapStructConfig;
import br.com.roanrobersson.rshop.domain.dto.input.UserInsert;
import br.com.roanrobersson.rshop.domain.dto.input.UserUpdate;
import br.com.roanrobersson.rshop.domain.dto.model.UserModel;
import br.com.roanrobersson.rshop.domain.model.User;

@Mapper(config = MapStructConfig.class)
public abstract class UserMapper {

	public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	public abstract UserModel toModel(User user);

	public abstract UserInsert toInsert(User user);

	public abstract UserUpdate toUpdate(User user);

	@Mapping(target = "password", ignore = true)
	public abstract User toUser(UserInsert userInsert);

	public Page<UserModel> toModelPage(Page<User> users) {
		return users.map(x -> this.toModel(x));
	};

	public abstract void update(UserUpdate userUpdate, @MappingTarget User user);
}
