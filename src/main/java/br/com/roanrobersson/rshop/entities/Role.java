package br.com.roanrobersson.rshop.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(columnDefinition = "CHAR(3)")
	@EqualsAndHashCode.Include
	private String id;
	
	@Column(unique = true, nullable=false, length=30)
	private String authority;
	
	@ManyToMany(mappedBy = "roles")
	@Setter(value = AccessLevel.NONE)
	private Set<User> users = new HashSet<>();

	public Role(String id, String authority) {
		super();
		this.id = id;
		this.authority = authority;
	}
}
