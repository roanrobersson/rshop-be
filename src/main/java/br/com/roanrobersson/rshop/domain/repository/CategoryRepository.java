package br.com.roanrobersson.rshop.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.domain.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

	Optional<Category> findByName(String name);

	Optional<Category> findById(UUID categoryId);

	void deleteById(UUID categoryId);
}
