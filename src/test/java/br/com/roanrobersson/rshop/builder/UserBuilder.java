package br.com.roanrobersson.rshop.builder;

import static br.com.roanrobersson.rshop.builder.AddressBuilder.aNonExistingAddress;
import static br.com.roanrobersson.rshop.builder.AddressBuilder.anExistingAddress;
import static br.com.roanrobersson.rshop.builder.RoleBuilder.aNonExistingRole;
import static br.com.roanrobersson.rshop.builder.RoleBuilder.anExistingRole;
import static br.com.roanrobersson.rshop.builder.VerificationTokenBuilder.aNonExpiredVerificationToken;
import static br.com.roanrobersson.rshop.builder.VerificationTokenBuilder.anExpiredVerificationToken;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.roanrobersson.rshop.api.v1.mapper.UserMapper;
import br.com.roanrobersson.rshop.api.v1.model.UserModel;
import br.com.roanrobersson.rshop.api.v1.model.input.UserInsert;
import br.com.roanrobersson.rshop.api.v1.model.input.UserUpdate;
import br.com.roanrobersson.rshop.domain.model.Address;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.model.VerificationToken;
import lombok.Getter;

@Getter
public class UserBuilder {

	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	public static final UUID EXISTING_ID = UUID.fromString("7c4125cc-8116-4f11-8fc3-f40a0775aec7");
	public static final UUID ANOTHER_EXISTING_ID = UUID.fromString("d16c83fe-3a2e-42b6-97b4-503b203647f6");
	public static final UUID NON_EXISTING_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
	public static final UUID DEPENDENT_ID = UUID.fromString("821e3c67-7f22-46af-978c-b6269cb15387");
	public static final String EXISTING_EMAIL = "cliente@gmail.com";
	public static final String ANOTHER_EXISTING_EMAIL = "operador@gmail.com";
	public static final String NON_EXISTING_EMAIL = "nonexistingemail@gmail.com";
	public static final Instant VALID_INSTANT = Instant.parse("2020-10-20T03:00:00Z");

	private UUID id = EXISTING_ID;
	private Set<Role> roles = new HashSet<>();
	private List<Address> addresses = new ArrayList<>();
	private VerificationToken verificationToken = null;
	private String firstName = "Operador";
	private String name = "Operador Sobrenome";
	private LocalDate birthDate = LocalDate.parse("1993-05-15");
	private String cpf = "86213939059";
	private String rg = "355144724";
	private String email = EXISTING_EMAIL;
	private String password = passwordEncoder.encode("a3g&3Pd#");;
	private String primaryTelephone = "54991200038";
	private String secondaryTelephone = "54991200038";
	private Instant verifiedAt = VALID_INSTANT;

	public static UserBuilder anUser() {
		return new UserBuilder();
	}

	public static UserBuilder anExistingUser() {
		UserBuilder builder = new UserBuilder();
		builder.id = EXISTING_ID;
		builder.roles.add(anExistingRole().build());
		builder.addresses.add(anExistingAddress().build());
		builder.verificationToken = aNonExpiredVerificationToken().build();
		builder.email = EXISTING_EMAIL;
		return builder;
	}

	public static UserBuilder aNonExistingUser() {
		UserBuilder builder = new UserBuilder();
		builder.id = EXISTING_ID;
		builder.roles.add(anExistingRole().build());
		builder.addresses.add(aNonExistingAddress().build());
		builder.verificationToken = aNonExpiredVerificationToken().build();
		builder.email = NON_EXISTING_EMAIL;
		return builder;
	}

	public UserBuilder withId(UUID id) {
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

	public UserBuilder withVerificationToken(VerificationToken verificationToken) {
		this.verificationToken = verificationToken;
		return this;
	}

	public UserBuilder withNonExpiredVerificationToken() {
		this.verificationToken = anExpiredVerificationToken().build();
		return this;
	}

	public UserBuilder withExpiredVerificationToken() {
		this.verificationToken = anExpiredVerificationToken().build();
		return this;
	}

	public UserBuilder withoutVerificationToken() {
		this.verificationToken = null;
		return this;
	}

	public UserBuilder withVerifiedAt(Instant verifiedAt) {
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
		User user = new User(id, roles, addresses, firstName, name, birthDate, cpf, rg, email, password,
				primaryTelephone, secondaryTelephone, VALID_INSTANT, VALID_INSTANT, verifiedAt, VALID_INSTANT);
		user.setVerificationToken(verificationToken);
		addresses.forEach(address -> address.setUser(user));
		if (verificationToken != null) {
			verificationToken.setUser(user);
		}
		return user;
	}

	public UserInsert buildInsert() {
		return UserMapper.INSTANCE.toUserInsert(build());
	}

	public UserUpdate buildUpdate() {
		return UserMapper.INSTANCE.toUserUpdate(build());
	}

	public UserModel buildModel() {
		return UserMapper.INSTANCE.toUserModel(build());
	}
}
