# Stage 1: Build the Maven project
FROM maven:3.9.11-eclipse-temurin-21-alpine@sha256:b06eb796808dbc800855a8d4a74b948bca95b3181e1f8ef44e909b04a2d7f47c AS build
WORKDIR /app

# Copy only the POM file to leverage Docker caching
COPY pom.xml .

# Download dependencies. This layer will be cached if pom.xml is not changed
RUN mvn -B dependency:go-offline

# Copy the rest of the source code and build the application
COPY src src
RUN mvn -B clean package -DskipTests

# Stage 2: Create a minimal distroless to make smaller the attack surface runtime image, and so because its distroless we dont need to make another user we and it come by default with non route user
FROM gcr.io/distroless/java21-debian12:nonroot AS runtime
WORKDIR /app

#Making an arg in case we switch the name soo to can pass new to the jarfile from the command line
ARG JAR_FILE=mobilemanagement-0.0.1-SNAPSHOT.jar

# Copy the JAR file built in the previous stage
COPY --from=build /app/target/mobilemanagement-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application listens on
EXPOSE 8080

# Command to run the application
CMD ["app.jar"]
