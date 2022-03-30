package br.com.roanrobersson.rshop.domain.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = { "id", "fileName" })
public class Image implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private UUID id;

	@Column(nullable = false, length = 255)
	private String fileName;

	@Column(nullable = false, length = 255)
	private String originalFileName;

	@Column(nullable = false, length = 50)
	private String contentType;

	@Column(nullable = false)
	private Integer fileSize;

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