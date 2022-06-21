[< Back](../README.md)

# Database :game_die::game_die::game_die:

The project uses the database migration tool [Flyway](https://flywaydb.org/) to create and update the database schema, and also in some cases to seed the database with some dummy data. In the table below are the default SGBDs and [Flyway](https://flywaydb.org/) behavior settings:
|Environment           |SGBD    |SGBD Address   |DB name    |Username     |Password     |DB creation | Flayway behavior                    |
|----------------------|--------|---------------|-----------|-------------|-------------|------------|-------------------------------------|
|Development (default) |MySQL 8 |localhost:3306 |rshop      |root         |             |automatic   |Update schema + Drop all data + Seed |
|Production            |MySQL 8 |ENV_VARIABLE   |rshop      |ENV_VARIABLE |ENV_VARIABLE |automatic   |Update schema                        |
|Integration tests     |MySQL 8 |localhost:3306 |rshop_test |root         |             |automatic   |Update schema                        |

The **Integration Tests** environment can also run the tests using [Testcontainers](https://www.testcontainers.org), in this way Docker containers with MySQL Server will always be automaticaly created to run the integration tests, and in the end they will be deleted. [Here](../docs/TESTCONTAINERS.md)'s how to run tests using Testcontainers.
