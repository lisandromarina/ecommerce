FROM maven:3.8.4-openjdk-17-slim AS build
COPY . .
RUN mvn clean package

FROM openjdk:17-jdk
COPY --from=build /target/e-commerce-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
CMD ["java", "-jar", "app.jar"]