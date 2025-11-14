package com.example.SafetyNet.model;

import lombok.Data;

import java.util.List;

@Data
public class MedicalRecord {
    private String firstName;
    private String lastName;
    private String birthDate;
    private List<String> medication;
    private List<String> allergies;
}
