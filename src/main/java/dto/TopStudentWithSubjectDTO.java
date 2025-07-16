package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class TopStudentWithSubjectDTO {
    private String regNumber;
    private Map<String, Double> scores;
    private Double totalScore;
}
