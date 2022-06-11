package br.com.roanrobersson.rshop.integration;

import java.io.IOException;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("it")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Testcontainers
public abstract class IT {

	private static final String DATABASE_NAME = "rshop_test";
	private static final String USER_NAME = "any";
	private static final String PASSWORD = "any";
	private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.29")
			.withDatabaseName(DATABASE_NAME).withUsername(USER_NAME).withPassword(PASSWORD);
	private static final boolean TESTCONTAINERS_ENABLED = Boolean
			.valueOf(System.getenv().get("RSHOP_TESTCONTAINERS_ENABLED"));

	@DynamicPropertySource
	private static void checkRunWithTestcontainers(DynamicPropertyRegistry registry) throws IOException {
		if (!TESTCONTAINERS_ENABLED)
			return;
		if (!mySQLContainer.isCreated() && !mySQLContainer.isRunning()) {
			mySQLContainer.start();
		}
		if (mySQLContainer.isCreated() && mySQLContainer.isRunning()) {
			registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
			registry.add("spring.datasource.password", mySQLContainer::getPassword);
			registry.add("spring.datasource.username", mySQLContainer::getUsername);
		}
	}
}