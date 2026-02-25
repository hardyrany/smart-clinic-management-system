# ====================================================
# STAGE 1: BUILD
# ====================================================
# Use Maven with Java 17 to compile and package the application
FROM maven:3.9.1-eclipse-temurin-17 AS build

# Set working directory inside the container
WORKDIR /app

# ---------------------------
# Optimize Docker layer caching
# ---------------------------

# Copy only the pom.xml first to leverage Docker cache
# Dependencies won't be re-downloaded unless pom.xml changes
COPY pom.xml .

# Download all dependencies without building the app
# This layer is cached until pom.xml changes
RUN mvn dependency:go-offline

# Copy the entire source code
COPY src ./src

# ---------------------------
# Build the application
# ---------------------------
# Package the application into a JAR file
# -DskipTests skips test execution to speed up build
# The resulting JAR will be in /app/target/
RUN mvn clean package -DskipTests


# ====================================================
# STAGE 2: RUNTIME
# ====================================================
# Use a smaller JRE image (not the full JDK) for the final container
# Alpine version is significantly smaller than standard JDK
FROM eclipse-temurin:17-jre-alpine

# Set working directory for the runtime container
WORKDIR /app

# ---------------------------
# Security: Create non-root user
# ---------------------------
# Running as non-root user is a security best practice
RUN addgroup -S springgroup && adduser -S springuser -G springgroup

# ---------------------------
# Copy the built artifact
# ---------------------------
# Copy the JAR file from the build stage
# The JAR name should match your project's artifactId and version
COPY --from=build /app/target/smart-clinic-0.0.1-SNAPSHOT.jar app.jar

# ---------------------------
# Switch to non-root user
# ---------------------------
# All subsequent commands will run as springuser
USER springuser:springgroup

# ---------------------------
# Configure container
# ---------------------------
# Document which port the application uses internally
# This is informational only; actual port mapping is done at runtime
EXPOSE 8080

# ---------------------------
# Health check (optional but recommended)
# ---------------------------
# Check if the application is healthy every 30 seconds
# Requires Spring Boot Actuator endpoint /actuator/health
# Uncomment if you have actuator dependency
# HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
#   CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# ---------------------------
# Startup command
# ---------------------------
# Use ENTRYPOINT for the main command
# This runs the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Optional: Add JVM options for production tuning
# ENTRYPOINT ["java", "-Xmx512m", "-Xms256m", "-jar", "app.jar"]