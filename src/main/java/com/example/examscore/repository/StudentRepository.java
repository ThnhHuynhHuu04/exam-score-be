package com.example.examscore.repository;

import com.example.examscore.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student> findTop10ByOrderByMathDescPhysicsDescChemistryDesc();
}

