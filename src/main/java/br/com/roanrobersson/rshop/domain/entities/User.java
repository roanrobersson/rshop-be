package br.com.roanrobersson.rshop.domain.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Setter(value = AccessLevel.NONE)
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "user")
	@Setter(value = AccessLevel.NONE)
	private List<Address> addresses = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "image_id")
	@Setter(value = AccessLevel.NONE)
	private Image image;

	@Column(nullable = false, length = 50)
	private String firstName;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE NOT NULL", updatable = false)
	private Instant birthDate;

	@Column(columnDefinition = "CHAR(11) NOT NULL")
	private String cpf;

	@Column(nullable = false, length = 14)
	private String rg;

	@Column(nullable = false, length = 50)
	private String email;

	@Column(nullable = false, length = 255)
	private String password;

	@Column(nullable = false, length = 11)
	private String primaryTelephone;

	@Column(nullable = false, length = 11)
	private String secondaryTelephone;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant verifiedAt;

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

	public boolean hasRole(String roleName) {
		for (Role role : roles) {
			if (role.getName().equals(roleName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().flatMap(role -> role.getPrivileges().stream())
				.map(privilege -> new SimpleGrantedAuthority(privilege.getName())).collect(Collectors.toSet());

	}

	@Override
	public String getUsername() {
		return getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return verifiedAt != null;
	}
}
