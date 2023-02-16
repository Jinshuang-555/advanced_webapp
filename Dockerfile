# Use an official OpenJDK runtime as a parent image
FROM arm64v8/openjdk:17-ea-16-jdk

ENV DEBUG=true

# Copy the JAR file to the container

COPY target/advanced_webapp-0.0.1-SNAPSHOT.jar /app/

WORKDIR /app

# Expose port for Spring Boot application
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "advanced_webapp-0.0.1-SNAPSHOT.jar"]

