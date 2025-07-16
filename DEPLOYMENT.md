# ExamScore Application - Deploy lên Render.com

## 🐳 Docker Setup

### Local Development với Docker

```bash
# Build và chạy với docker-compose
docker-compose up --build

# Chỉ chạy database
docker-compose up postgres

# Chạy application riêng
docker build -t exam-score .
docker run -p 8080:8080 exam-score
```

## ☁️ Deploy lên Render.com

#### Bước 1: Chuẩn bị

1. Push code lên GitHub repository
2. Tạo account tại [Render.com](https://render.com)

#### Bước 2: Tạo PostgreSQL Database

1. Trong Render Dashboard, click "New +" → "PostgreSQL"
2. Điền thông tin:
   - Name: `exam-score-db`
   - Database: `examscore`
   - User: `postgres`
3. Lưu lại Database URL sau khi tạo

#### Bước 3: Tạo Web Service

1. Click "New +" → "Web Service"
2. Connect GitHub repository
3. Cấu hình:
   - **Environment**: `Docker`
   - **Branch**: `main`
   - **Dockerfile Path**: `./Dockerfile`
   - **Region**: Singapore (gần Việt Nam nhất)

#### Bước 4: Environment Variables

Thêm các biến môi trường sau:

```
DATABASE_URL=postgresql://postgres:password@host:5432/examscore
DB_USERNAME=postgres
DB_PASSWORD=your_password
PORT=8080
SPRING_PROFILES_ACTIVE=prod
```

#### Bước 5: Deploy

- Click "Create Web Service"
- Render sẽ tự động build và deploy
- Quá trình build có thể mất 5-10 phút
- Sau khi deploy thành công, bạn sẽ có URL public để truy cập ứng dụng

## 🔧 Environment Variables cho Render

### Required Variables (Bắt buộc)

- `DATABASE_URL`: PostgreSQL connection string từ Render Database
- `DB_USERNAME`: Database username (thường là postgres)
- `DB_PASSWORD`: Database password từ Render Database
- `PORT`: Application port (luôn là 8080)

### Optional Variables (Tùy chọn)

- `SPRING_PROFILES_ACTIVE`: Set thành `prod` cho production
- `JAVA_OPTS`: JVM options (ví dụ: `-Xmx512m -Xms256m`)

## 🚀 API Endpoints

Sau khi deploy thành công, ứng dụng sẽ có các endpoints:

- **Health Check**: `GET /actuator/health`
- **API Documentation**: `GET /swagger-ui.html`
- **Main API**: `GET /api/*`

## 🔍 Monitoring & Logs trên Render

- **Logs**: Render Dashboard → Service → Logs tab
- **Metrics**: Render Dashboard → Service → Metrics tab
- **Environment**: Render Dashboard → Service → Environment tab

## 🛠️ Troubleshooting

### Common Issues

1. **Database Connection Error**

   - Kiểm tra DATABASE_URL có đúng format không
   - Đảm bảo PostgreSQL database đã được tạo trên Render
   - Kiểm tra username và password

2. **Application Won't Start**

   - Kiểm tra Java version trong Dockerfile (phải là 17+)
   - Kiểm tra PORT environment variable
   - Xem logs trên Render để debug

3. **Build Failures**
   - Đảm bảo Dockerfile syntax đúng
   - Kiểm tra Maven dependencies trong pom.xml
   - Clear cache và rebuild

### Useful Commands để test local

```bash
# Local testing
docker-compose up --build
docker logs <container_name>

# Health check
curl http://localhost:8080/actuator/health

# Build only
docker build -t exam-score .
```

## 📝 Lưu ý quan trọng

- **Database**: Sử dụng PostgreSQL từ Render (miễn phí 90 ngày, sau đó $7/month)
- **SSL**: Render tự động cung cấp HTTPS
- **Scaling**: Có thể scale up/down theo nhu cầu
- **Backup**: Render tự động backup database
- **Custom Domain**: Có thể add custom domain nếu cần

## 🎯 Checklist Deploy thành công

- [ ] Code đã push lên GitHub
- [ ] PostgreSQL database đã tạo trên Render
- [ ] Web Service đã tạo và connect với GitHub repo
- [ ] Environment variables đã được set đúng
- [ ] Build và deploy thành công (không có lỗi)
- [ ] Health check endpoint hoạt động: `/actuator/health`
- [ ] API endpoints hoạt động bình thường

## 🚀 Quick Start Guide

### Bước nhanh để deploy:

1. **Push code lên GitHub**
2. **Tạo Database trên Render:**
   - New → PostgreSQL → Name: `exam-score-db`
3. **Tạo Web Service:**
   - New → Web Service → Connect GitHub repo
   - Environment: Docker
4. **Set Environment Variables:**
   ```
   DATABASE_URL=<copy_from_render_database>
   DB_USERNAME=postgres
   DB_PASSWORD=<copy_from_render_database>
   PORT=8080
   SPRING_PROFILES_ACTIVE=prod
   ```
5. **Deploy và chờ build hoàn thành**

Sau khi hoàn thành, bạn sẽ có một URL public để truy cập ứng dụng!

- Kiểm tra PORT environment variable
- Xem logs để debug

3. **Build Failures**
   - Đảm bảo Dockerfile syntax đúng
   - Kiểm tra Maven dependencies
   - Clear cache và rebuild

### Useful Commands

```bash
# Local testing
docker-compose up --build
docker logs <container_name>

# Health check
curl http://localhost:8080/actuator/health

# Build only
docker build -t exam-score .
```

## 📝 Notes

- Sử dụng PostgreSQL cho production (không dùng H2)
- Enable SSL trong production nếu cần
- Cấu hình CORS cho frontend connections
- Setup monitoring và alerting cho production
- Backup database định kỳ
