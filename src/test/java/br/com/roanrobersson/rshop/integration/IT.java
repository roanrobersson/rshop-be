package br.com.roanrobersson.rshop.integration;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("it")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Testcontainers
public abstract class IT {

	protected static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.29")
			.withDatabaseName("rshop_test").withUsername("any").withPassword("any");

	// Override application.it.properties
//	@DynamicPropertySource
//	static void mySQLProperties(DynamicPropertyRegistry registry) {
//		registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
//		registry.add("spring.datasource.password", mySQLContainer::getPassword);
//		registry.add("spring.datasource.username", mySQLContainer::getUsername);
//	}

}
