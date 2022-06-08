package br.com.roanrobersson.rshop.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

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
@ToString(of = { "id", "name", "categories" })
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "char(36)")
	@Type(type = "uuid-char")
	@EqualsAndHashCode.Include
	private UUID id;

	@ManyToMany
	@JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	@Setter(value = AccessLevel.NONE)
	private Set<Category> categories = new HashSet<>();

	@Column(unique = true, nullable = false, length = 12)
	private String sku;

	@Column(unique = true, nullable = false, length = 127)
	private String name;

	@Column(columnDefinition = "text")
	private String description;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(nullable = false, length = 255)
	private String imgUrl;

	@CreationTimestamp
	@Column(updatable = false)
	private OffsetDateTime createdAt;

	@UpdateTimestamp
	private OffsetDateTime updatedAt;

	@Builder
	public Product(UUID id, Set<Category> categories, String sku, String name, String description, BigDecimal price,
			String imgUrl, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
		this.id = id;
		this.sku = sku;
		if (categories != null) {
			this.categories.addAll(categories);
		}
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
