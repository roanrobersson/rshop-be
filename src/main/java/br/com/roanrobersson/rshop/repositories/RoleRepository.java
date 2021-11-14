package br.com.roanrobersson.rshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.entities.Category;
import br.com.roanrobersson.rshop.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByAuthority(String authority);
}
