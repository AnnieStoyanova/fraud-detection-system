# Step 1: Use OpenJDK 21 image to build the application
FROM eclipse-temurin:21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy your project’s pom.xml or build.gradle (for dependency resolution)
COPY pom.xml /app/

# If you're using Maven or Gradle to build the app, copy the source code
COPY src /app/src/

# Build the app
RUN apt-get update && apt-get install -y maven
RUN mvn clean install -DskipTests

# Step 2: Use a smaller runtime-only image for the actual container
FROM eclipse-temurin:21

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar /app/app.jar

# Expose the port your Java app will be running on
EXPOSE 8081

# Set the command to run your app
CMD ["java", "-jar", "/app/app.jar"]
