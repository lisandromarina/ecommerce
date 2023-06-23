# Use the official Maven image to build the application
FROM maven:3.8.4-openjdk-17 AS builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .

# Download the dependencies
RUN mvn dependency:go-offline -B

# Copy the project source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Use a smaller base image for the runtime
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your Spring Boot app listens on
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "app.jar"]