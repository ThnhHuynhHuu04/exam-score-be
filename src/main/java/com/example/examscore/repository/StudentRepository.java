package com.example.examscore.repository;

import com.example.examscore.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    Optional<Student> findByRegNumber(String regNumber);

    boolean existsByRegNumber(String regNumber);

    @Query(value = "SELECT * FROM student_score_distribution", nativeQuery = true)
    List<Object[]> fetchScoreDistribution();

    @Modifying
    @Query(value = "REFRESH MATERIALIZED VIEW student_score_distribution", nativeQuery = true)
    void refreshScoreDistributionView();



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
