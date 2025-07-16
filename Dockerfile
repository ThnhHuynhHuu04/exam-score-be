# Multi-stage build để giảm kích thước image
FROM openjdk:17-jdk-slim as builder

# Thiết lập working directory
WORKDIR /app

# Copy Maven wrapper và pom.xml
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build application
RUN ./mvnw clean package -DskipTests

# Final stage
FROM openjdk:17-jre-slim

# Thiết lập working directory
WORKDIR /app

# Copy jar file từ builder stage
COPY --from=builder /app/target/*.jar app.jar

# Tạo user non-root để chạy application
RUN addgroup --system spring && adduser --system spring --ingroup spring
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
