package com.example.SafetyNet.model;

import lombok.Data;
import java.util.List;

@Data
public class SafetyNetData {
    private List<Person> persons;
    private List<FireStation> firestations;
    private List<MedicalRecord> medicalrecords;
}