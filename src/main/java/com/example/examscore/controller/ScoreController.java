package com.example.examscore.controller;

import com.example.examscore.model.Student;
import com.example.examscore.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class ScoreController {
    private final ScoreService scoreService;

    @GetMapping("{id}")
    public ResponseEntity<Student> getScore(@PathVariable String id) {
        return ResponseEntity.ok(scoreService.getStudentById(id));
    }

}
