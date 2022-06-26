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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "aProduct", toBuilder = true)
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
	@Singular
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

	public static ProductBuilder aProduct() {
		UUID uuid = UUID.fromString("00000000-0000-4000-0000-000000000000");
		OffsetDateTime offsetDateTime = OffsetDateTime.parse("2020-10-20T03:00:00Z");
		return new ProductBuilder()
				.id(uuid)
				.name("Keyboard")
				.description("A black keyboard for gaming")
				.price(BigDecimal.valueOf(50.00))
				.imgUrl("http://www.ficticiousimagehost.com/image.png")
				.createdAt(offsetDateTime)
				.updatedAt(offsetDateTime);
	}
}
