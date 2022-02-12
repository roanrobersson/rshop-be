package br.com.roanrobersson.rshop.domain.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = { "id", "user", "expiryDate" })
public class VerificationToken implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(unique = true, nullable = false, length = 255)
	private String token;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE NOT NULL")
	private Instant expiryDate;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE NOT NULL")
	private Instant createdAt;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updatedAt;

	@PrePersist
	public void prePersist() {
		createdAt = Instant.now();
	}

	@PreUpdate
	public void preUpdate() {
		updatedAt = Instant.now();
	}
}
