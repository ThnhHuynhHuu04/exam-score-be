#!/usr/bin/env bash
# build_and_deploy.sh - Script để build và deploy lên Render

# Build application
echo "Building application..."
./mvnw clean package -DskipTests

# Build Docker image
echo "Building Docker image..."
docker build -t exam-score-app .

echo "Build completed successfully!"
echo "Ready to deploy to Render!"

# Hướng dẫn deploy:
echo ""
echo "=== HƯỚNG DẪN DEPLOY LÊN RENDER ==="
echo "1. Push code lên GitHub repository"
echo "2. Truy cập https://render.com và tạo account"
echo "3. Tạo Web Service mới từ GitHub repo"
echo "4. Chọn Docker environment"
echo "5. Thiết lập Environment Variables:"
echo "   - DATABASE_URL: URL PostgreSQL từ Render"
echo "   - DB_USERNAME: username database"
echo "   - DB_PASSWORD: password database"
echo "   - PORT: 8080"
echo "6. Deploy!"
