# rShop REST API :gear: <a name="top"></a>

rShop is a REST API being built essentially with [Spring Boot](https://spring.io/guides/gs/spring-boot), with the purpose of learning some of the [Spring ecosystem projects](https://spring.io/projects) and others related to the development of REST APIs, as well as maintaining a reference repository of problem solutions, organization and good development practices. I plan to convert it to microservices architecture soon.

The project still doesn't have a concrete idea of its scope, requirements and business rules, so at the moment it only has a few features close to an E-commerce, but it can evolve into anything else.

Initially I had plans to simultaneously build a React application to consume this API, but the project is paused for now, the current project code and UI prototype can be found here: [GitHub Repository](https://github.com/roanrobersson/rshop-fe) | [Figma prototype](https://www.figma.com/file/zleS0uCt7mzjfEaw1orh0i/rShop?node-id=0%3A1)<br />

## Technologies used :dna:
- [Java 11](https://www.java.com)
- [Maven 3.8.1](https://maven.apache.org)
- [Spring Boot 2.6.4](https://spring.io/projects/spring-boot)
- [SonarQube 8.9.9-community](https://www.sonarqube.org)
- [Docker 4.9.0](https://www.docker.com)
- [MySQL server 8.0.29](https://www.mysql.com)
- [SpringDoc-openapi 1.6.9](https://springdoc.org/)
- [Testcontainers 1.17.2](https://www.testcontainers.org)
- [SendGrid 4.7.6](https://sendgrid.com)
- [Flyway 8.0.5](https://flywaydb.org/)
- [Project Lombok 1.18.22](https://projectlombok.org)
- [MapStruct 1.5.1.Final](https://mapstruct.org)
- [Amazon S3](https://aws.amazon.com/pt/s3)
- [JUnit 5](https://junit.org/junit5)
- [Mockito 4](https://site.mockito.org/)

## Features :star2:
- Authorization with OAuth 2.0
- Category, Role, Privilege, Product and User CRUD
- Image Upload using Amazon S3

## Running locally 🔨

### Prerequisites:
- Java ^11 (full JDK not a JRE)
- Maven ^3.8.1
- Docker ^4.9.0 (Recommended) or a local [MySQL server ^8.0.29](https://dev.mysql.com/downloads/mysql/)

### Steps
1) On the command line, clone the repository
    ```bash
    git clone https://github.com/roanrobersson/rshop-be
    ```
    
2) Enter the project folder
    ```bash
    cd rshop-be
    ```

3) Download and install the MySQL server 8.0.29 or run it in a Docker container:
    * If you prefer install the MySQL server: [Download MySQL](https://dev.mysql.com/downloads/mysql/)
    * If you prefer a Docker container, on the command line run this command:
        ```bash
        docker run --name MySQL-rShop -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=true -d mysql:8.0.29
        ```
    
4) Execute the project
    ```bash
    mvn spring-boot:run
    ```

  You can then access rshop API here: http://localhost:8080 <br />
  API documentation with Swagger UI: http://localhost:8080/swagger-ui/index.html

## Informations :memo:
- [Environments](docs/ENVIRONMENTS.md)
- [Database](docs/DATABASE.md)
- [System environment variables](docs/SYS_ENV_VARIABLES.md)

## How to :book:
- [Use Postman to send rShop API requests](docs/POSTMAN.md)
- [Work with rShop on Spring Tool Suite IDE](docs/IDE.md)
- [Inspect code quality with SonarQube](docs/SONARQUBE.md)
- [Run integration tests using Testcontainers](docs/TESTCONTAINERS.md)

<br />
<a href="#top">Back to top ^</a>
