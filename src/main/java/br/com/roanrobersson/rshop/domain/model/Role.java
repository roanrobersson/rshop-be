package br.com.roanrobersson.rshop.domain.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AccessLevel;
import lombok.Builder;
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
@ToString(of = { "id", "name", "privileges" })
public class Role implements Serializable {

	public static final String CLIENT = "ROLE_CLIENT";

	public static final String OPERATOR = "ROLE_OPERATOR";

	public static final String ADMIN = "ROLE_ADMIN";

	public static final String TEST = "ROLE_TEST";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private UUID id;

	@ManyToMany
	@JoinTable(name = "role_privilege", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "privilege_id"))
	@Setter(value = AccessLevel.NONE)
	private Set<Privilege> privileges = new HashSet<>();

	@Column(unique = true, nullable = false, length = 30)
	private String name;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE NOT NULL", updatable = false)
	private Instant createdAt;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updatedAt;

	@Builder
	public Role(UUID id, Set<Privilege> privileges, String name, Instant createdAt, Instant updatedAt) {
		this.id = id;
		if (privileges != null) {
			this.privileges.addAll(privileges);
		}
		this.name = name;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	@PrePersist
	public void prePersist() {
		createdAt = Instant.now();
	}

	@PreUpdate
	public void preUpdate() {
		updatedAt = Instant.now();
	}
}
