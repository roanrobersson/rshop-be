package br.com.roanrobersson.rshop.domain.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "nick", "user_id" }) })
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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "char(36)")
	@Type(type = "uuid-char")
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
	private String addressLine;

	@Column(nullable = false, length = 6)
	private String number;

	@Column(nullable = false, length = 30)
	private String neighborhood;

	@Column(nullable = false, length = 75)
	private String city;

	@Column(nullable = false, length = 75)
	private String state;

	@Column(columnDefinition = "char(2) not null")
	private String uf;

	@Column(columnDefinition = "char(8) not null")
	private String postalCode;

	@Column(length = 75)
	private String complement;

	@Column(nullable = false, length = 75)
	private String referencePoint;

	@Column(nullable = false, length = 11)
	private String telephone;

	@CreationTimestamp
	@Column(updatable = false)
	private OffsetDateTime createdAt;

	@UpdateTimestamp
	private OffsetDateTime updatedAt;
}
