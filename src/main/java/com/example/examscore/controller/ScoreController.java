package com.example.examscore.controller;

import com.example.examscore.model.Student;
import com.example.examscore.service.ScoreService;
import dto.SubjectStatDTO;
import dto.TopStudentWithSubjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class ScoreController {
    private final ScoreService scoreService;

    @GetMapping
    public String home() {
        return "<html>"
                + "<h2>Chào mừng đến với ExamScore API!</h2>"
                + "<p>Để xem tài liệu và thử API, hãy truy cập <a href='/swagger-ui/index.html' target='_blank'>Swagger UI</a>.</p>"
                + "</html>";
    }

    @GetMapping("/score/{id}")
    public ResponseEntity<Student> getScore(@PathVariable String id) {
        return ResponseEntity.ok(scoreService.getStudentById(id));
    }

    @GetMapping("/score-stat")
    public ResponseEntity<List<SubjectStatDTO>> getScoreDistribution() {
        return ResponseEntity.ok(scoreService.getScoreStat());
    }

    @GetMapping("/top")
    public ResponseEntity<List<TopStudentWithSubjectDTO>> getTopStudents(
            @RequestParam(defaultValue = "A") String group,
            @RequestParam(defaultValue = "10") int limit

    ){
        return ResponseEntity.ok(scoreService.getTopStudentByGroup(group, limit));
    }

}
