package br.com.roanrobersson.rshop.builder;

import java.time.OffsetDateTime;

import br.com.roanrobersson.rshop.domain.dto.input.AddressInput;
import br.com.roanrobersson.rshop.domain.dto.model.AddressModel;
import br.com.roanrobersson.rshop.domain.mapper.AddressMapper;
import br.com.roanrobersson.rshop.domain.model.Address;
import br.com.roanrobersson.rshop.domain.model.User;

public class AddressBuilder {

	public static final Long EXISTING_ID = 1L;
	public static final Long ANOTHER_EXISTING_ID = 2L;
	public static final Long NON_EXISTING_ID = 999999L;
	public static final String EXISTING_NICK = "Home";
	public static final String ANOTHER_EXISTING_NICK = "Work";
	public static final String NON_EXISTING_NICK = "Non existing nick";
	public static final OffsetDateTime VALID_DATETIME = OffsetDateTime.parse("2020-10-20T03:00:00Z");

	private Long id = EXISTING_ID;
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
		Address address = new Address();
		address.setId(id);
		address.setUser(user);
		address.setNick(nick);
		address.setMain(main);
		address.setAddressLine(addressLine);
		address.setNumber(number);
		address.setNeighborhood(neighborhood);
		address.setCity(city);
		address.setState(state);
		address.setUf(uf);
		address.setPostalCode(postalCode);
		address.setComplement(complement);
		address.setReferencePoint(referencePoint);
		address.setTelephone(telephone);
		address.setUpdatedAt(VALID_DATETIME);
		address.setCreatedAt(VALID_DATETIME);
		return address;
	}

	public AddressInput buildInput() {
		return AddressMapper.INSTANCE.toInput(build());
	}

	public AddressModel buildModel() {
		return AddressMapper.INSTANCE.toModel(build());
	}
}
