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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "nick", "user_id" }) })
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "anAddress", toBuilder = true)
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

	public static AddressBuilder anAddress() {
		UUID uuid = UUID.fromString("00000000-0000-4000-0000-000000000000");
		OffsetDateTime offsetDateTime = OffsetDateTime.parse("2020-10-20T03:00:00Z");
		return new AddressBuilder().id(uuid).nick("Casa 2").addressLine("Rua Mazzoleni").number("999")
				.neighborhood("Beira Lagoa").complement("Fundos").referencePoint("Próx mercado Zorzi")
				.city("Porto Alegre").state("Rio Grande do Sul").uf("RS").postalCode("12345678")
				.telephone("54981457832").createdAt(offsetDateTime).updatedAt(offsetDateTime);
	}
}
