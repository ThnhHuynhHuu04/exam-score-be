# Multi-stage build để giảm kích thước image
FROM eclipse-temurin:17-jdk-alpine AS builder

# Thiết lập working directory
WORKDIR /app

# Set environment variables for Maven
ENV MAVEN_OPTS="-Dfile.encoding=UTF-8 -Dproject.build.sourceEncoding=UTF-8"

# Copy Maven wrapper và pom.xml
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .

# Make mvnw executable
RUN chmod +x ./mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build application với encoding UTF-8
RUN ./mvnw clean package -DskipTests -Dfile.encoding=UTF-8 -Dproject.build.sourceEncoding=UTF-8

# Final stage
FROM eclipse-temurin:17-jre-alpine

# Install curl cho health check
RUN apk add --no-cache curl

# Thiết lập working directory
WORKDIR /app

# Copy jar file từ builder stage
COPY --from=builder /app/target/*.jar app.jar

# Tạo user non-root để chạy application
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Expose port
EXPOSE 8080

# Set JVM options để tối ưu cho container
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
