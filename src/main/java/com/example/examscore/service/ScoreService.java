package com.example.examscore.service;

import com.example.examscore.model.Student;
import com.example.examscore.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private  final StudentRepository studentRepository;

    public Student getStudentById(String id) {
        return studentRepository.findById(id).orElse(null);
    }

}
