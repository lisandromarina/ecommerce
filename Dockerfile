#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk
COPY --from=build /target/e-commerce-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]