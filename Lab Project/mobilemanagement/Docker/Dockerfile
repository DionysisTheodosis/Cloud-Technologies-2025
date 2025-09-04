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

# Stage 2: Create a minimal runtime image
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

# Argument to allow flexible JAR file names
ARG JAR_FILE=mobilemanagement-0.0.1-SNAPSHOT.jar

# Copy the JAR file from the build stage using the ARG
COPY --from=build /app/target/${JAR_FILE} app.jar

# Set a non-root user for security reasons
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
USER appuser

# Expose the port your application listens on
EXPOSE 8080

# Command to run the application
CMD ["java","-jar","app.jar"]

