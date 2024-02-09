package br.com.roanrobersson.rshop.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.domain.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query("SELECT obj FROM Role obj " //
			+ "LEFT JOIN FETCH obj.privileges " //
			+ "WHERE obj IN :roles")
	List<Role> findRolesWithPrivileges(List<Role> roles);

	Optional<Role> findByName(String name);
}
