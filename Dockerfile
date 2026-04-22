# Use Java 21 (matches your project's java.version)
FROM eclipse-temurin:21-jdk-alpine

# Metadata
LABEL maintainer="mamatha@example.com"
LABEL description="Customer Service Spring Boot CRUD application"

# Set working directory inside container
WORKDIR /app

# Copy the built jar into the container
COPY target/customer-service-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]