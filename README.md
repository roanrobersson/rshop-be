# rShop API

rShop is a Spring Boot application built using Maven.

# Technologies used
- Spring Boot

# Running locally

Prerequisites:
- Java 11 or newer
- Maven
- Docker

```bash
# clone the repository
git clone https://github.com/roanrobersson/rshop-be

# enter the project folder
cd rshop-be

# create a Docker MySQL container
docker run --name MySQL-rShop -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=true -d mysql:8.0.29

# execute the project
mvn spring-boot:run
```
You can then access rshop API here: http://localhost:8080/
API documentation with Swagger UI: http://localhost:8080/swagger-ui/index.html

