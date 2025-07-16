package com.example.examscore.repository;

import com.example.examscore.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    Optional<Student> findByRegNumber(String regNumber);

    boolean existsByRegNumber(String regNumber);

    @Query(value = """
        SELECT 'math' AS subject,
               COUNT(CASE WHEN math >= 8 THEN 1 END) AS level_above_8,
               COUNT(CASE WHEN math >= 6 AND math < 8 THEN 1 END) AS level_6_to_8,
               COUNT(CASE WHEN math >= 4 AND math < 6 THEN 1 END) AS level_4_to_6,
               COUNT(CASE WHEN math < 4 THEN 1 END) AS level_below_4
        FROM student
        UNION ALL
        SELECT 'literature', 
               COUNT(CASE WHEN literature >= 8 THEN 1 END),
               COUNT(CASE WHEN literature >= 6 AND literature < 8 THEN 1 END),
               COUNT(CASE WHEN literature >= 4 AND literature < 6 THEN 1 END),
               COUNT(CASE WHEN literature < 4 THEN 1 END)
        FROM student
        UNION ALL
        SELECT 'foreign_language', 
               COUNT(CASE WHEN foreign_language >= 8 THEN 1 END),
               COUNT(CASE WHEN foreign_language >= 6 AND foreign_language < 8 THEN 1 END),
               COUNT(CASE WHEN foreign_language >= 4 AND foreign_language < 6 THEN 1 END),
               COUNT(CASE WHEN foreign_language < 4 THEN 1 END)
        FROM student
        UNION ALL
        SELECT 'physics', 
               COUNT(CASE WHEN physics >= 8 THEN 1 END),
               COUNT(CASE WHEN physics >= 6 AND physics < 8 THEN 1 END),
               COUNT(CASE WHEN physics >= 4 AND physics < 6 THEN 1 END),
               COUNT(CASE WHEN physics < 4 THEN 1 END)
        FROM student
        UNION ALL
        SELECT 'chemistry', 
               COUNT(CASE WHEN chemistry >= 8 THEN 1 END),
               COUNT(CASE WHEN chemistry >= 6 AND chemistry < 8 THEN 1 END),
               COUNT(CASE WHEN chemistry >= 4 AND chemistry < 6 THEN 1 END),
               COUNT(CASE WHEN chemistry < 4 THEN 1 END)
        FROM student
        UNION ALL
        SELECT 'biology', 
               COUNT(CASE WHEN biology >= 8 THEN 1 END),
               COUNT(CASE WHEN biology >= 6 AND biology < 8 THEN 1 END),
               COUNT(CASE WHEN biology >= 4 AND biology < 6 THEN 1 END),
               COUNT(CASE WHEN biology < 4 THEN 1 END)
        FROM student
        UNION ALL
        SELECT 'history', 
               COUNT(CASE WHEN history >= 8 THEN 1 END),
               COUNT(CASE WHEN history >= 6 AND history < 8 THEN 1 END),
               COUNT(CASE WHEN history >= 4 AND history < 6 THEN 1 END),
               COUNT(CASE WHEN history < 4 THEN 1 END)
        FROM student
        UNION ALL
        SELECT 'geography', 
               COUNT(CASE WHEN geography >= 8 THEN 1 END),
               COUNT(CASE WHEN geography >= 6 AND geography < 8 THEN 1 END),
               COUNT(CASE WHEN geography >= 4 AND geography < 6 THEN 1 END),
               COUNT(CASE WHEN geography < 4 THEN 1 END)
        FROM student
        UNION ALL
        SELECT 'civic_education', 
               COUNT(CASE WHEN civic_education >= 8 THEN 1 END),
               COUNT(CASE WHEN civic_education >= 6 AND civic_education < 8 THEN 1 END),
               COUNT(CASE WHEN civic_education >= 4 AND civic_education < 6 THEN 1 END),
               COUNT(CASE WHEN civic_education < 4 THEN 1 END)
        FROM student
        """, nativeQuery = true)
    List<Object[]> fetchScoreDistribution();

    @Query(value = """
    SELECT reg_number, math, physics, chemistry,
           (math + physics + chemistry) AS total_score
    FROM student
    WHERE math IS NOT NULL AND physics IS NOT NULL AND chemistry IS NOT NULL
    ORDER BY total_score DESC
    LIMIT 10
    """, nativeQuery = true)
    List<Object[]> findTop10GroupAStudents();
}
