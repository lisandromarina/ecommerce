#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/ecommerce/src
COPY pom.xml /home/ecommerce
RUN mvn -f /home/ecommerce/pom.xml clean package

FROM openjdk:17-jdk
VOLUME /tmp
COPY --from=build target/e-commerce-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]