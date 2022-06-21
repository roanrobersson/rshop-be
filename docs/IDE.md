[< Back](../README.md)

# Work with rShop on Spring Tool Suite IDE

### Prerequisites:
- [Java ^11](https://www.java.com) (full JDK not a JRE)
- [Maven ^3.8.1](https://maven.apache.org)
- [Docker ^4.9.0](https://www.docker.com)
- [Spring Tool Suite ^4](https://spring.io/tools)

	
### Steps

1) On the command line, clone the repository
    ```bash
    git clone https://github.com/roanrobersson/rshop-be
    ```
    
2) On the command line, Create a [Docker's MySQL](https://hub.docker.com/_/mysql) container
    ```bash
    docker run --name MySQL-rShop -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=true -d mysql:8.0.29
    ```

3) On the [Spring Tool Suite](https://spring.io/tools), import the project
    ```
	File > Import > Maven > Existing Maven project
    ```
    
4) On the [Spring Tool Suite](https://spring.io/tools), install the [Lombok](https://projectlombok.org) plugin
    ```
	Help > Install New Software
	In the "Work with" field type: "https://projectlombok.org/p2"
	Check the Lombok box and proceed with the installation by clicking Next in the next windows...
	You need to actually quit Eclipse and start it again; the regular restart is not good enough. 
    ```
    
5) On the Spring Tool Suite, import and active the [formatter profile](rshop-formatter-profile.xml)
    ```
    Project root > Properties > Java Code Style > Formatter
    - Check the "Enable project specific settings" box
    - Import the file found in this path: rshop/docs/files/rshop-formatter-profile.xml
    - Apply and close
	```
