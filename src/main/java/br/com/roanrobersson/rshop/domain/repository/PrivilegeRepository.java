package br.com.roanrobersson.rshop.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.domain.model.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, UUID> {

	Optional<Privilege> findById(UUID privilegeId);
}
