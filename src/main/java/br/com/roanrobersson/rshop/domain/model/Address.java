package br.com.roanrobersson.rshop.domain.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"nick", "user_id"}) })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = { "id", "nick", "main" })
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	@Column(nullable = false, length = 20)
	private String nick;

	@Column(nullable = false)
	private Boolean main;

	@Column(nullable = false, length = 75)
	private String address;

	@Column(nullable = false, length = 6)
	private String number;

	@Column(nullable = false, length = 30)
	private String neighborhood;

	@Column(nullable = false, length = 75)
	private String city;

	@Column(nullable = false, length = 75)
	private String state;

	@Column(columnDefinition = "CHAR(2) NOT NULL")
	private String uf;

	@Column(columnDefinition = "CHAR(8) NOT NULL")
	private String postalCode;

	@Column(length = 75)
	private String complement;

	@Column(nullable = false, length = 75)
	private String referencePoint;

	@Column(nullable = false, length = 11)
	private String telephone;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE NOT NULL", updatable = false)
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
