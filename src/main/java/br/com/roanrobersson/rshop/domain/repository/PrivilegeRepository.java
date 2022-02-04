package br.com.roanrobersson.rshop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.domain.model.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
	
}
