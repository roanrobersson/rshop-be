package br.com.roanrobersson.rshop.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.domain.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

	@Query("SELECT obj " + "FROM Role obj " + "LEFT JOIN FETCH obj.privileges " + "WHERE obj IN :roles")
	List<Role> findRolesWithPrivileges(List<Role> roles);

	@Query("SELECT obj " + "FROM Role obj " + "LEFT JOIN FETCH obj.privileges " + "WHERE obj.id = :id")
	Optional<Role> findByIdWithPrivileges(UUID id);

	Optional<Role> findByName(String name);

	void deleteById(UUID roleId);
}
