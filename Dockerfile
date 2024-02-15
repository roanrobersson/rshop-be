FROM openjdk:11

COPY target/rshop-0.0.1-SNAPSHOT.jar api.jar
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]