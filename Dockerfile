# Stage 1: Build the application
FROM gradle:7.5.1-jdk17 AS build
WORKDIR /app

# Copy the Gradle wrapper and project files
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./
COPY src src/

# Build the application without running the tests
RUN gradle build -x test --no-daemon

# Stage 2: Create the Docker image
FROM openjdk:17-jdk-slim
VOLUME /tmp

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
