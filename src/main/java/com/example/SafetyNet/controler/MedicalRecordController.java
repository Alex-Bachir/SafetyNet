package com.example.SafetyNet.controler;

import com.example.SafetyNet.model.MedicalRecord;
import com.example.SafetyNet.service.MedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicalRecord")
@Slf4j
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public List<MedicalRecord> getMedicalRecords() {
        return  medicalRecordService.getAllMedicalRecords();
    }

    @GetMapping("/{firstName}/{lastName}/{birthDate}")
    public ResponseEntity<Optional<MedicalRecord>> getMedicalRecord(@PathVariable String firstName, @PathVariable String lastName, @PathVariable String birthDate, @PathVariable List<String> medications, @PathVariable List<String> allergies, @RequestBody MedicalRecord medicalRecord) {
        try {
            Optional<MedicalRecord> getMedicRec = medicalRecordService.updateMedicalRecord(firstName, lastName, birthDate, medications, allergies);
            return ResponseEntity.ok(getMedicRec);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{firstName}/{lastName}/{birthDate}")
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName, @PathVariable String birthDate, @PathVariable List<String> medications, @PathVariable List<String> allergies) {
        boolean deleted = medicalRecordService.deleteMedicalRecord(firstName, lastName, birthDate, medications, allergies);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
