# Use Maven image to build the application
FROM maven:3.8.1-openjdk-17-slim AS builder

# Set the working directory
WORKDIR /app

# Copy the entire project directory into the container
COPY . .

# Build the application with Maven
RUN mvn clean package -DskipTests

# Use a smaller base image for the final Docker image
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file built in the previous stage
COPY --from=builder /app/target/*.jar /app/*.jar

# Copy the credentials.json file into the container
COPY src/main/resources/credentials.json /app/credentials.json

# Expose the port that the Spring Boot application will run on
EXPOSE 8082

# Command to run the Spring Boot application when the container starts
ENTRYPOINT ["java", "-jar", "/app/*.jar"]
