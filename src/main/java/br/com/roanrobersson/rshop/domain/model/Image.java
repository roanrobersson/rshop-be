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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "char(36)")
	@Type(type = "uuid-char")
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

	@CreationTimestamp
	@Column(updatable = false)
	private OffsetDateTime createdAt;

	@UpdateTimestamp
	private OffsetDateTime updatedAt;
}