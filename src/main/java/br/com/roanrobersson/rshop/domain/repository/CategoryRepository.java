package br.com.roanrobersson.rshop.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.domain.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

	Optional<Category> findById(UUID categoryId);

	Optional<Category> findByName(String name);

	void deleteById(UUID categoryId);
}
