package br.com.roanrobersson.rshop.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.domain.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	List<Role> findAll(Sort sort);
	
	@Query("SELECT obj " 
			+ "FROM Role obj " 
			+ "JOIN FETCH obj.privileges " 
			+ "WHERE obj IN :roles")
	List<Role> findRolesWithPrivileges(List<Role> roles);
	
	@Query("SELECT obj " 
			+ "FROM Role obj " 
			+ "JOIN FETCH obj.privileges " 
			+ "WHERE obj.id = :id")
	Optional<Role> findByIdWithPrivileges(Long id);
	
	Optional<Role> findByName(String name);
}
