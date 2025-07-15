package com.example.examscore.service;



import com.example.examscore.model.Student;
import com.example.examscore.repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataSeeder {

    private final StudentRepository studentRepository;

    @PostConstruct
    public void seedData() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/diem_thi_thpt_2024.csv"), StandardCharsets.UTF_8))) {

            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // skip header
                    continue;
                }

                String[] fields = line.split(",",-1);

                fields = Arrays.copyOf(fields,11);

                Student student = new Student(
                        fields[0], // regNumber
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
            }

            System.out.println(" Dữ liệu đã được load thành công.");
        } catch (Exception e) {
            System.err.println(" Lỗi khi đọc file CSV: " + e.getMessage());
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
