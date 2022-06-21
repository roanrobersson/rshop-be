[< Back](../README.md)

# Run integration tests using Testcontainers :package::package::package:

With Testcontainers, all Integration tests that extend the [abstract class IT](../src/test/java/br/com/roanrobersson/rshop/integration/IT.java) will ignore the current database settings from the file [application-it.properties](../src/test/resources/application-it.properties) and will run using a Docker container automatically created, and in the end the container will be deleted. By default all integration tests will run in same container.

### Prerequisites:
- Docker ^4.9.0

### Steps to enable
1) Create and set the system environment variable "[RSHOP_TESTCONTAINERS_ENABLED](SYS_ENV_VARIABLES.md)" with the value "**true**"

### Steps to disable
1) Set the "[RSHOP_TESTCONTAINERS_ENABLED](SYS_ENV_VARIABLES.md)" with the value "**false**"
