#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /home/ecommerce
COPY src /home/ecommerce/src
COPY pom.xml /home/ecommerce
RUN mvn -f /home/ecommerce/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk
WORKDIR /app
VOLUME /tmp
COPY --from=build target/e-commerce-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]