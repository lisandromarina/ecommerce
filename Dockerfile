FROM maven:3.8.4-openjdk-17-slim AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk
COPY --from=build /target/e-commerce-0.0.1-SNAPSHOT.jar app.jar
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh  # Provide execute permissions
EXPOSE 8082
CMD ["./wait-for-it.sh", "mysql-db:3306", "--", "java", "-jar", "app.jar"]

