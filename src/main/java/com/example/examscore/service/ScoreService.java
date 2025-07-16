package com.example.examscore.service;

import com.example.examscore.model.Student;
import com.example.examscore.repository.StudentRepository;
import dto.SubjectStatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private  final StudentRepository studentRepository;

    public Student getStudentById(String id) {
        return studentRepository.findById(id).orElse(null);
    }

    public List<SubjectStatDTO> getScoreStat(){
        return studentRepository.fetchScoreDistribution().stream()
                .map(row -> new SubjectStatDTO(
                        row[0].toString(),
                        ((Number) row[1]).longValue(),
                        ((Number) row[2]).longValue(),
                        ((Number) row[3]).longValue(),
                        ((Number) row[4]).longValue()
                )).toList();
    }

}
