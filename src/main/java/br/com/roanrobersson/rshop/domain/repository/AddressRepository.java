package br.com.roanrobersson.rshop.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.domain.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	Page<Address> findAllByUserId(Long userId, Pageable pageable);

	Optional<Address> findFirstByUserIdAndMain(Long userId, boolean main);

	Optional<Address> findByUserIdAndId(Long userId, Long id);

	Optional<Address> findByUserIdAndNick(Long userId, String nick);
}
