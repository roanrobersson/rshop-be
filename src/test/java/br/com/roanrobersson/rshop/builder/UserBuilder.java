package br.com.roanrobersson.rshop.builder;

import static br.com.roanrobersson.rshop.builder.AddressBuilder.aNonExistingAddress;
import static br.com.roanrobersson.rshop.builder.AddressBuilder.anExistingAddress;
import static br.com.roanrobersson.rshop.builder.RoleBuilder.aNonExistingRole;
import static br.com.roanrobersson.rshop.builder.RoleBuilder.anExistingRole;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.roanrobersson.rshop.domain.dto.input.UserInsert;
import br.com.roanrobersson.rshop.domain.dto.input.UserUpdate;
import br.com.roanrobersson.rshop.domain.dto.model.UserModel;
import br.com.roanrobersson.rshop.domain.mapper.UserMapper;
import br.com.roanrobersson.rshop.domain.model.Address;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.model.User;
import lombok.Getter;

@Getter
public class UserBuilder {

	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	public static final Long EXISTING_ID = 1L;
	public static final Long ANOTHER_EXISTING_ID = 2L;
	public static final Long NON_EXISTING_ID = 999999L;
	public static final Long DEPENDENT_ID = 1L;
	public static final String EXISTING_EMAIL = "client@gmail.com";
	public static final String ANOTHER_EXISTING_EMAIL = "operator@gmail.com";
	public static final String NON_EXISTING_EMAIL = "nonexistingemail@gmail.com";
	public static final OffsetDateTime VALID_DATETIME = OffsetDateTime.parse("2020-10-20T03:00:00Z");

	private Long id = EXISTING_ID;
	private Set<Role> roles = new HashSet<>();
	private List<Address> addresses = new ArrayList<>();
	private String firstName = "Operator";
	private String name = "Operator LastName";
	private LocalDate birthDate = LocalDate.parse("1993-05-15");
	private String cpf = "86213939059";
	private String rg = "355144724";
	private String email = EXISTING_EMAIL;
	private String password = passwordEncoder.encode("a3g&3Pd#");;
	private String primaryTelephone = "54991200038";
	private String secondaryTelephone = "54991200038";
	private OffsetDateTime verifiedAt = VALID_DATETIME;

	public static UserBuilder anUser() {
		return new UserBuilder();
	}

	public static UserBuilder anExistingUser() {
		UserBuilder builder = new UserBuilder();
		builder.id = EXISTING_ID;
		builder.roles.add(anExistingRole().build());
		builder.addresses.add(anExistingAddress().build());
		builder.email = EXISTING_EMAIL;
		return builder;
	}

	public static UserBuilder aNonExistingUser() {
		UserBuilder builder = new UserBuilder();
		builder.id = EXISTING_ID;
		builder.roles.add(anExistingRole().build());
		builder.addresses.add(aNonExistingAddress().build());
		builder.email = NON_EXISTING_EMAIL;
		return builder;
	}

	public UserBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public UserBuilder withExistingId() {
		this.id = EXISTING_ID;
		return this;
	}

	public UserBuilder withAnotherExistingId() {
		this.id = ANOTHER_EXISTING_ID;
		return this;
	}

	public UserBuilder withNonExistingId() {
		this.id = NON_EXISTING_ID;
		return this;
	}

	public UserBuilder withDependentId() {
		this.id = DEPENDENT_ID;
		return this;
	}

	public UserBuilder withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public UserBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public UserBuilder withBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
		return this;
	}

	public UserBuilder withCpf(String cpf) {
		this.cpf = cpf;
		return this;
	}

	public UserBuilder withRg(String rg) {
		this.rg = rg;
		return this;
	}

	public UserBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public UserBuilder withExistingEmail() {
		this.email = EXISTING_EMAIL;
		return this;
	}

	public UserBuilder withAnotherExistingEmail() {
		this.email = ANOTHER_EXISTING_EMAIL;
		return this;
	}

	public UserBuilder withNonExistingEmail() {
		this.email = NON_EXISTING_EMAIL;
		return this;
	}

	public UserBuilder withPassword(String password) {
		this.password = password;
		return this;
	}

	public UserBuilder withPrimaryTelephone(String primaryTelephone) {
		this.primaryTelephone = primaryTelephone;
		return this;
	}

	public UserBuilder withSecondaryTelephone(String secondaryTelephone) {
		this.secondaryTelephone = secondaryTelephone;
		return this;
	}

	public UserBuilder withVerifiedAt(OffsetDateTime verifiedAt) {
		this.verifiedAt = verifiedAt;
		return this;
	}

	public UserBuilder withRole(Role role) {
		roles.add(role);
		return this;
	}

	public UserBuilder withExistingRole() {
		roles.add(anExistingRole().build());
		return this;
	}

	public UserBuilder withNonExistingRole() {
		roles.add(aNonExistingRole().build());
		return this;
	}

	public UserBuilder withoutRoles() {
		roles.clear();
		return this;
	}

	public UserBuilder withAddress(Address address) {
		addresses.add(address);
		return this;
	}

	public UserBuilder withExistingAddress() {
		addresses.add(anExistingAddress().build());
		return this;
	}

	public UserBuilder withNonExistingAddress() {
		addresses.add(aNonExistingAddress().build());
		return this;
	}

	public UserBuilder withoutAddresses() {
		addresses.clear();
		return this;
	}

	public User build() {
		User user = new User();
		user.setId(id);
		user.getRoles().addAll(roles);
		user.getAddresses().addAll(addresses);
		user.setFirstName(firstName);
		user.setName(name);
		user.setBirthDate(birthDate);
		user.setCpf(cpf);
		user.setRg(rg);
		user.setEmail(email);
		user.setPassword(password);
		user.setPrimaryTelephone(primaryTelephone);
		user.setSecondaryTelephone(secondaryTelephone);
		user.setVerifiedAt(verifiedAt);
		user.setLastLoginAt(VALID_DATETIME);
		user.setCreatedAt(VALID_DATETIME);
		user.setUpdatedAt(VALID_DATETIME);
		return user;
	}

	public UserInsert buildInsert() {
		return UserMapper.INSTANCE.toInsert(build());
	}

	public UserUpdate buildUpdate() {
		return UserMapper.INSTANCE.toUpdate(build());
	}

	public UserModel buildModel() {
		return UserMapper.INSTANCE.toModel(build());
	}
}
