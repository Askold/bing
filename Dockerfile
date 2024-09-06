# Use an official Gradle image as the base image for building the application
FROM gradle:7.4.2-jdk17 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle build files to the container
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle

# Copy the rest of the application source code to the container
COPY src ./src

# Build the application
RUN ./gradlew clean build -x test

# Use the OpenJDK 17 image for the runtime
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the builder stage to the runtime stage
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
