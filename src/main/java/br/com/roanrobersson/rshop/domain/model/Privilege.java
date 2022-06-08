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
@ToString(of = { "id", "name" })
public class Privilege implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "char(36)")
	@Type(type = "uuid-char")
	@EqualsAndHashCode.Include
	private UUID id;

	@Column(unique = true, nullable = false, length = 30)
	private String name;

	@Column(nullable = false, length = 100)
	private String description;

	@CreationTimestamp
	@Column(updatable = false)
	private OffsetDateTime createdAt;

	@UpdateTimestamp
	private OffsetDateTime updatedAt;

	@Builder
	public Privilege(UUID id, String name, String description, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
