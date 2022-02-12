package br.com.roanrobersson.rshop.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.domain.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

	List<Address> findAllByUserId(UUID userId, Sort sort);

	Optional<Address> findFirstByUserIdAndMain(UUID userId, boolean main);

	Optional<Address> findByUserIdAndId(UUID userId, UUID id);

	Optional<Address> findByUserIdAndNick(UUID userId, String nick);

	void deleteById(UUID addressId);
}
