#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
COPY src /home/ecommerce/src
COPY pom.xml /home/ecommerce
RUN mvn -f /home/ecommerce/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk
COPY --from=build /home/ecommerce/target/*SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]