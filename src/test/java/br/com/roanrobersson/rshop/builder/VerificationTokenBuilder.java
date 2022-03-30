package br.com.roanrobersson.rshop.builder;

import java.time.Instant;
import java.util.UUID;

import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.model.VerificationToken;
import lombok.Getter;

@Getter
public class VerificationTokenBuilder {

	public static final UUID NON_EXISTING_ID = UUID.fromString("00000000-0000-4000-0000-000000000000");
	public static final Instant NON_EXPIRED_INSTANT = Instant.parse("3000-10-20T03:00:00Z");
	public static final Instant EXPIRED_INSTANT = Instant.parse("1990-08-10T03:00:00Z");
	public static final Instant VALID_INSTANT = Instant.parse("1990-08-09T03:00:00Z");
	public static final UUID VALID_UUID = UUID.fromString("753dad79-2a1f-4f5c-bbd1-317a53587518");

	private UUID id = UserBuilder.EXISTING_ID;
	private User user = null;
	private UUID token = VALID_UUID;
	private Instant expiryAt = NON_EXPIRED_INSTANT;

	public static VerificationTokenBuilder aVerificationToken() {
		return new VerificationTokenBuilder();
	}

	public static VerificationTokenBuilder aNonExpiredVerificationToken() {
		VerificationTokenBuilder builder = new VerificationTokenBuilder();
		builder.id = UserBuilder.EXISTING_ID;
		builder.user = null;
		builder.expiryAt = NON_EXPIRED_INSTANT;
		return builder;
	}

	public static VerificationTokenBuilder anExpiredVerificationToken() {
		VerificationTokenBuilder builder = new VerificationTokenBuilder();
		builder.id = UserBuilder.EXISTING_ID;
		builder.user = null;
		builder.expiryAt = EXPIRED_INSTANT;
		return builder;
	}

	public static VerificationTokenBuilder aNonExistingVerificationToken() {
		VerificationTokenBuilder builder = new VerificationTokenBuilder();
		builder.id = UserBuilder.NON_EXISTING_ID;
		builder.user = null;
		builder.expiryAt = NON_EXPIRED_INSTANT;
		return builder;
	}

	public VerificationTokenBuilder withId(UUID id) {
		this.id = id;
		return this;
	}

	public VerificationTokenBuilder withExistingId() {
		this.id = UserBuilder.EXISTING_ID;
		return this;
	}

	public VerificationTokenBuilder withAnotherExistingId() {
		this.id = UserBuilder.ANOTHER_EXISTING_ID;
		return this;
	}

	public VerificationTokenBuilder withNoExistingId() {
		this.id = UserBuilder.NON_EXISTING_ID;
		return this;
	}

	public VerificationTokenBuilder withUser(User user) {
		this.user = user;
		return this;
	}

	public VerificationTokenBuilder withExpiryAt(Instant expiryAt) {
		this.expiryAt = expiryAt;
		return this;
	}

	public VerificationTokenBuilder withNonExpired() {
		expiryAt = NON_EXPIRED_INSTANT;
		return this;
	}

	public VerificationTokenBuilder withExpired() {
		expiryAt = EXPIRED_INSTANT;
		return this;
	}

	public VerificationToken build() {
		VerificationToken verificationToken = new VerificationToken(user, token, expiryAt, VALID_INSTANT,
				VALID_INSTANT);
		if (user != null) {
			user.setVerificationToken(verificationToken);
		}
		return verificationToken;
	}
}
