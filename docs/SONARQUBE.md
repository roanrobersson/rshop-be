[< Back](../README.md)
# Inspect code quality with SonarQube :mag_right:

### Prerequisites:
- Docker ^4.9.0

### Steps
1) On the command line, create and run a Docker's SonarQube container
    ```bash 
    docker run --name SonarQube-rShop -p 9000:9000 sonarqube:8.9.9-community
    ```

2) On the browser, access SonarQube UI using the default credentials (it will ask you to create a new password):
    ```bash  
    http://localhost:9000
    Login: admin
    Password: admin
    ```
3) On the browser, access SonarQube UI and generate a access token:
    ```bash  
    http://localhost:9000/account/security
    ```

4) On the command line, execute maven install (run this in project root)
    ```bash  
    mvn clean install
    ```

5) On the command line, scan the project **using the authentication token generated in sep 3** (run this in project root)
    ```bash  
    mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=**place-the-generated-token-here**
    ```

6) On the browser, access SonarQube UI and see the project report:
    ```bash
    http://localhost:9000
    ```
