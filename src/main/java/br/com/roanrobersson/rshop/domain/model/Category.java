package br.com.roanrobersson.rshop.domain.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "aCategory", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = { "id", "name" })
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "char(36)")
	@Type(type = "uuid-char")
	@EqualsAndHashCode.Include
	private UUID id;

	@Column(unique = true, nullable = false, length = 127)
	private String name;

	@CreationTimestamp
	@Column(updatable = false)
	private OffsetDateTime createdAt;

	@UpdateTimestamp
	private OffsetDateTime updatedAt;

	public static CategoryBuilder aCategory() {
		UUID uuid = UUID.fromString("00000000-0000-4000-0000-000000000000");
		OffsetDateTime offsetDateTime = OffsetDateTime.parse("2020-10-20T03:00:00Z");
		return new CategoryBuilder().id(uuid).name("Electronic").createdAt(offsetDateTime).updatedAt(offsetDateTime);
	}
}
