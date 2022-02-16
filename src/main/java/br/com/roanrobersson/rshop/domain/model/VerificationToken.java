package br.com.roanrobersson.rshop.domain.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = { "user", "token", "expiryAt" })
public class VerificationToken implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final long EXPIRATION_IN_SECONDS = 60 * 60 * 24;

	@Id
	@Column(name = "user_id")
	@Setter(value = AccessLevel.NONE)
	private UUID id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(unique = true)
	private UUID token;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE NOT NULL")
	private Instant expiryAt;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE NOT NULL")
	private Instant createdAt;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updatedAt;

	public VerificationToken(User user, UUID token) {
		this.user = user;
		this.token = token;
	}

	@PrePersist
	public void prePersist() {
		createdAt = Instant.now();
		expiryAt = createdAt.plusSeconds(EXPIRATION_IN_SECONDS);
	}

	@PreUpdate
	public void preUpdate() {
		updatedAt = Instant.now();
	}
}
