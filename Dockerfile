# Use Ubuntu as base image
FROM ubuntu:latest

# Set the working directory in the container
WORKDIR /app

# Update and install necessary dependencies
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

# Copy the project files into the container
COPY . .

# Build the Spring Boot application using Maven
RUN mvn clean install

# Expose the port that the Spring Boot application will run on
EXPOSE 3000

#COPY --from=build /target/spring-mvc-login-okta-0.0.1-SNAPSHOT.jar app.jar

# Command to run the Spring Boot application
CMD ["java", "-Dspring.profiles.active=dev", "-jar", "target/springnosql-cloud.jar"]