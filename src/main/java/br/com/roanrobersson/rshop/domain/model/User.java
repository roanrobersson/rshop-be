package br.com.roanrobersson.rshop.domain.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

//@Table(name = "user", schema = "public") Necessary only for PostgreSQL database
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "anUser", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = { "id", "firstName" })
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "char(36)")
	@Type(type = "uuid-char")
	@EqualsAndHashCode.Include
	private UUID id;

	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Setter(value = AccessLevel.NONE)
	@Singular
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@Setter(value = AccessLevel.NONE)
	@Singular
	private List<Address> addresses = new ArrayList<>();

	@Column(nullable = false, length = 50)
	private String firstName;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false)
	private LocalDate birthDate;

	@Column(columnDefinition = "char(11) not null")
	private String cpf;

	@Column(nullable = false, length = 14)
	private String rg;

	@Column(unique = true, nullable = false, length = 50)
	private String email;

	@Column(nullable = false, length = 255)
	private String password;

	@Column(nullable = false, length = 11)
	private String primaryTelephone;

	@Column(nullable = false, length = 11)
	private String secondaryTelephone;

	private OffsetDateTime verifiedAt;

	private OffsetDateTime lastLoginAt;

	@CreationTimestamp
	@Column(updatable = false)
	private OffsetDateTime createdAt;

	@UpdateTimestamp
	private OffsetDateTime updatedAt;

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
		return roles
				.stream()
				.flatMap(role -> role.getPrivileges().stream())
				.map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
				.collect(Collectors.toSet());

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

	public static UserBuilder anUser() {
		UUID uuid = UUID.fromString("00000000-0000-4000-0000-000000000000");
		OffsetDateTime offsetDateTime = OffsetDateTime.parse("2020-10-20T03:00:00Z");
		return new UserBuilder()
				.id(uuid)
				.firstName("Madalena")
				.name("Madalena Bernardon")
				.birthDate(LocalDate.parse("1993-01-16"))
				.rg("222182428")
				.cpf("67709960065")
				.email("madalenabernardon@gmail.com")
				.password("12345678")
				.primaryTelephone("54998223654")
				.secondaryTelephone("54334178")
				.verifiedAt(offsetDateTime)
				.lastLoginAt(offsetDateTime)
				.createdAt(offsetDateTime)
				.updatedAt(offsetDateTime);
	}
}
