package br.com.roanrobersson.rshop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.domain.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	List<Address> findAllByUserId(Long userId, Sort sort);

	Optional<Address> findFirstByUserIdAndMain(Long userId, boolean main);

	Optional<Address> findByUserIdAndId(Long userId, Long id);

	Optional<Address> findByUserIdAndNick(Long userId, String nick);
}
