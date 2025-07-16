package com.example.examscore.service;

import com.example.examscore.model.Student;
import com.example.examscore.repository.StudentRepository;
import dto.SubjectStatDTO;
import dto.TopStudentWithSubjectDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private  final StudentRepository studentRepository;
    @PersistenceContext
    private EntityManager entityManager;

    private static final Map<String,List<String>> groupSubjects = Map.of(
            "A",List.of("math","physics","chemistry"),
            "B", List.of("math", "chemistry", "biology"),
            "C", List.of("literature", "history", "geography"),
            "D", List.of("math", "literature", "foreign_language")

    );

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

    public List<TopStudentWithSubjectDTO> getTopStudentByGroup(String group,int limit){
        List<String> subject = groupSubjects.get(group.toUpperCase());
        if(subject==null || subject.size()!=3){
            throw new IllegalArgumentException("Invalid group: " + group);

        }

        String s1 = subject.get(0);
        String s2 = subject.get(1);
        String s3 = subject.get(2);

        String sql = String.format("""
                SELECT reg_number, %s, %s, %s,
                                   (%s + %s + %s) AS total_score
                            FROM student
                            WHERE %s IS NOT NULL AND %s IS NOT NULL AND %s IS NOT NULL
                            ORDER BY total_score DESC
                            LIMIT :limit
                """,s1,s2,s3,
                    s1,s2,s3,
                    s1,s2,s3
                );
        List<Object[]> results = entityManager
                .createNativeQuery(sql)
                .setParameter("limit",limit)
                .getResultList();
        return results.stream().map(row->{
            Map<String,Double> scores = Map.of(
                    s1,((Number)row[1]).doubleValue(),
                    s2,((Number)row[2]).doubleValue(),
                    s3,((Number)row[3]).doubleValue()
            );
            return new TopStudentWithSubjectDTO(
                    row[0].toString(),
                    scores,
                    ((Number)row[4]).doubleValue()
            );
        }).toList();
    }

}
