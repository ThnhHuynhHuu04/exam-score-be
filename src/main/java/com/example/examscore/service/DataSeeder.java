package com.example.examscore.service;



import com.example.examscore.model.Student;
import com.example.examscore.repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class DataSeeder {

    private final StudentRepository studentRepository;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void init() {
        System.out.println("Starting data seeder");
        executorService.submit(this::seedData);
    }

    @Transactional
    public void seedData() {
        System.out.println("Bắt đầu kiểm tra và seed dữ liệu...");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/diem_thi_thpt_2024.csv"), StandardCharsets.UTF_8))) {

            String line;
            boolean isFirstLine = true;
            int addedCount = 0;
            int skippedCount = 0;
            int errorCount = 0;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // skip header
                    continue;
                }

                try {
                    String[] fields = line.split(",",-1);
                    fields = Arrays.copyOf(fields,11);

                    String regNumber = fields[0];
                    

                    if (studentRepository.existsByRegNumber(regNumber)) {
                        skippedCount++;
                        continue;
                    }

                    Student student = new Student(
                            regNumber, // regNumber
                            parseDouble(fields[1]), // math
                            parseDouble(fields[2]), // literature
                            parseDouble(fields[3]), // foreignLanguage
                            parseDouble(fields[4]), // physics
                            parseDouble(fields[5]), // chemistry
                            parseDouble(fields[6]), // biology
                            parseDouble(fields[7]), // history
                            parseDouble(fields[8]), // geography
                            parseDouble(fields[9]), // civicEducation
                            fields[10].isBlank() ? null : fields[10]              // foreignId
                    );

                    studentRepository.save(student);
                    addedCount++;
                    
                    if (addedCount % 1000 == 0) {
                        System.out.println("Đã thêm " + addedCount + " students...");
                    }
                } catch (Exception e) {
                    errorCount++;
                    System.err.println("Lỗi khi xử lý dòng: " + line + " - " + e.getMessage());

                }
            }

            System.out.println("=== KẾT QUẢ SEED ===");
            System.out.println("Đã thêm: " + addedCount + " students");
            System.out.println("Đã bỏ qua (tồn tại): " + skippedCount + " students");
            System.out.println("Lỗi: " + errorCount + " dòng");
            System.out.println("Tổng số students trong DB: " + studentRepository.count());
            
        } catch (Exception e) {
            System.err.println("Lỗi khi đọc file CSV: " + e.getMessage());
        }
        finally {
            executorService.shutdown();
            System.out.println("Finished data seeder");
        }
    }

    private Double parseDouble(String str) {
        try {
            return (str == null || str.isBlank()) ? null : Double.parseDouble(str.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
