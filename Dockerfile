# Use OpenJDK 17 as base image
FROM openjdk:17-alpine

# Set environment variables for the Spring Boot app
ENV PORT='8080'
ENV APP_NAME='Default'
ENV DATABASE_URI='mongodb://localhost:27017/default'
ENV DATABASE_NAME='default'
ENV JWT_SECRET='secret'
ENV JWT_EXPIRATION='86400000'
ENV JWT_REFRESH_EXP='86400000'

# Create app directory
RUN mkdir /app

# Set the working directory in the container
WORKDIR /app

# Copy the Maven configuration files
COPY pom.xml /app/pom.xml
COPY src /app/src

# Build the Spring Boot application using Maven
RUN apk add --no-cache maven && mvn -f /app/pom.xml clean package

# Expose the port the app runs on
EXPOSE ${PORT}

# Command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "/app/target/sua-auth-0.0.1-SNAPSHOT.jar"]