package com.example.examscore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    private String regNumber;

    private Double math;
    private Double literature;
    private Double foreignLanguage;
    private Double physics;
    private Double chemistry;
    private Double biology;
    private Double history;
    private Double geography;
    private Double civicEducation;
    private String foreignId;

}
