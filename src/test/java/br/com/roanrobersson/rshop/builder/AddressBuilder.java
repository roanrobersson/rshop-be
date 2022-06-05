package br.com.roanrobersson.rshop.builder;

import java.time.Instant;
import java.util.UUID;

import br.com.roanrobersson.rshop.api.v1.mapper.AddressMapper;
import br.com.roanrobersson.rshop.api.v1.model.AddressModel;
import br.com.roanrobersson.rshop.api.v1.model.input.AddressInput;
import br.com.roanrobersson.rshop.domain.model.Address;
import br.com.roanrobersson.rshop.domain.model.User;

public class AddressBuilder {

	public static final UUID EXISTING_ID = UUID.fromString("37783d1e-f631-408f-8796-e4da82c275e0");
	public static final UUID ANOTHER_EXISTING_ID = UUID.fromString("7e88c136-b11c-4879-920f-1193db6fef0f");
	public static final UUID NON_EXISTING_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
	public static final String EXISTING_NICK = "Home";
	public static final String ANOTHER_EXISTING_NICK = "Work";
	public static final String NON_EXISTING_NICK = "Non existing nick";
	public static final Instant VALID_INSTANT = Instant.parse("2020-10-20T03:00:00Z");

	private UUID id = EXISTING_ID;
	private User user = null;
	private String nick = EXISTING_NICK;
	private Boolean main = true;
	private String addressLine = "Brockton Avenue";
	private String number = "65";
	private String neighborhood = "Center";
	private String city = "Massapequa";
	private String state = "New York";
	private String uf = "NY";
	private String postalCode = "99700000";
	private String complement = "AP 2";
	private String referencePoint = "Next to the happy market";
	private String telephone = "54998204476";

	public static AddressBuilder aAddress() {
		return new AddressBuilder();
	}

	public static AddressBuilder anExistingAddress() {
		AddressBuilder builder = new AddressBuilder();
		builder.id = EXISTING_ID;
		builder.user = null;
		builder.nick = EXISTING_NICK;
		return builder;
	}

	public static AddressBuilder aNonExistingAddress() {
		AddressBuilder builder = new AddressBuilder();
		builder.id = NON_EXISTING_ID;
		builder.user = null;
		builder.nick = NON_EXISTING_NICK;
		return builder;
	}

	public AddressBuilder withUser(User user) {
		this.user = user;
		return this;
	}

	public Address build() {
		return new Address(id, user, nick, main, addressLine, number, neighborhood, city, state, uf, postalCode, complement,
				referencePoint, telephone, VALID_INSTANT, VALID_INSTANT);
	}

	public AddressInput buildInput() {
		return AddressMapper.INSTANCE.toInput(build());
	}

	public AddressModel buildModel() {
		return AddressMapper.INSTANCE.toModel(build());
	}
}
