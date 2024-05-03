# Use a base image with maven and java installed.
FROM maven:3.8.4-openjdk-17 AS build

# Set Working Directory
WORKDIR /app

#Copy the Maven project file
COPY pom.xml .

# Download Maven dependencies (only is the pom.xml file has changed)
RUN mvn dependency:go-offline -B 

# Copy the application source code
COPY src ./src 

# Build the application
RUN mvn package -DskipTests

# Use a lightweight JDK base image for runtime
FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the packaged JAR file from the previos stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port of the applicationruns on
EXPOSE 8080

# Command to run the application
CMD [ "java", "-jar", "app.jar" ]




#FROM openjdk:17-slim
#EXPOSE 8080
#COPY target/book-store.jar book-store.jar
#CMD [ "java" , "-jar" , "/book-store.jar"]
