# ExamScore

Demo online: [https://exam-score-be.onrender.com](https://exam-score-be.onrender.com)

Ứng dụng quản lý và phân tích điểm thi THPT Quốc Gia 2024.

## Tính năng chính

- Import dữ liệu điểm thi từ file CSV
- Lưu trữ thông tin học sinh và điểm các môn
- API truy vấn thống kê điểm, top học sinh theo từng khối
- Seed dữ liệu tự động, hỗ trợ tiếp tục khi gặp lỗi
- Docker hỗ trợ triển khai môi trường production

## Công nghệ sử dụng

- Java 17, Spring Boot
- PostgreSQL
- Docker, Docker Compose
- Lombok

## Hướng dẫn chạy dự án

### 1. Chuẩn bị môi trường

- Cài đặt Docker & Docker Compose
- Tạo file `.env` với nội dung:
  ```env
  DATABASE_URL=jdbc:postgresql://db:5432/examscore
  DB_USERNAME=your_db_user
  DB_PASSWORD=your_db_password
  PORT=8080
  ```

### 2. Build & chạy bằng Docker Compose

```sh
docker-compose -f docker-compose.prod.yml up --build
```

### 3. Truy cập ứng dụng

- API chạy tại: `http://localhost:8080`
- Demo online: [https://exam-score-be.onrender.com](https://exam-score-be.onrender.com)

## Cấu trúc thư mục

```
src/main/java/com/example/examscore/      # Source code chính
src/main/resources/                       # File cấu hình, CSV, SQL
scripts/                                  # Script build/deploy
```

## Seed dữ liệu

- Khi khởi động, dữ liệu từ `diem_thi_thpt_2024.csv` sẽ được import vào database.
- Nếu gặp lỗi, lần chạy sau sẽ tiếp tục import các dòng còn thiếu.

## Liên hệ & đóng góp

- Tác giả: ThnhHuynhHuu04
- Đóng góp qua GitHub Pull Request

---

**Lưu ý:**

- Đảm bảo file CSV đúng định dạng, không sửa header.
- Đổi thông tin kết nối DB trong `.env` cho phù hợp môi trường của bạn.
