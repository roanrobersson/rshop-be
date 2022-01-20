package br.com.roanrobersson.rshop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.domain.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	List<Role> findAll(Sort sort);
	
	Optional<Role> findByName(String name);
}
