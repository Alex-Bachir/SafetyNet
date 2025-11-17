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

    @GetMapping("/{firstName}/{lastName}")
    public ResponseEntity<MedicalRecord> getMedicalRecordByName(
            @PathVariable String firstName,
            @PathVariable String lastName) {

        log.info("Requête GET reçue pour {} {}", firstName, lastName);
        Optional<MedicalRecord> medicalRecord = medicalRecordService.findByName(firstName, lastName);

        return medicalRecord
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{firstName}/{lastName}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody MedicalRecord updatedRecord) {

        log.info("Requête PUT reçue pour mettre à jour {} {}", firstName, lastName);

        Optional<MedicalRecord> updated = medicalRecordService.updateMedicalRecord(
                firstName,
                lastName,
                updatedRecord
        );

        return updated
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName, @PathVariable List<String> medications, @PathVariable List<String> allergies) {
        boolean deleted = medicalRecordService.deleteMedicalRecord(firstName, lastName, medications, allergies);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
