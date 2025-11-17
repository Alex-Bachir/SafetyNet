package com.example.SafetyNet.service;

import com.example.SafetyNet.model.MedicalRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MedicalRecordService {

    private final DataLoaderService dataLoaderService;

    public MedicalRecordService(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return dataLoaderService.getData().getMedicalrecords();
    }

    public Optional<MedicalRecord> findByName(String firstName, String lastName) {
        log.info("Recherche du dossier médical pour {} {}", firstName, lastName);
        return Optional.ofNullable(dataLoaderService.getData().getMedicalrecords())
                .orElse(Collections.emptyList())
                .stream()
                .filter(mr -> mr.getFirstName().equalsIgnoreCase(firstName)
                        && mr.getLastName().equalsIgnoreCase(lastName))
                .findFirst();
    }

    // --------------------------------  POST ------------------------------------ //

    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        List<MedicalRecord> medicalRecords = Optional.ofNullable(dataLoaderService.getData().getMedicalrecords())
                .orElseGet(() -> {
                    List<MedicalRecord> medicalRecordList = new ArrayList<>();
                    dataLoaderService.getData().getMedicalrecords().add(medicalRecord);
                    return medicalRecordList;
                });
        medicalRecords.add(medicalRecord);
        return medicalRecord;

    }

    // ------------------------------- UPDATE -----------------------------------//

    public Optional<MedicalRecord> updateMedicalRecord(String firstName,
                                                       String lastName,
                                                       String birthDate,
                                                       List<String> medications,
                                                       List<String> allergies) {
        List<MedicalRecord> medicalRecords = Optional.ofNullable(dataLoaderService.getData().getMedicalrecords())
                .orElse(Collections.emptyList());

        return medicalRecords.stream()
                .filter(mr -> mr.getFirstName().equalsIgnoreCase(firstName)
                        && mr.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .map(existingRecord -> {
                    if (birthDate != null) {
                        existingRecord.setBirthDate(birthDate);
                    }
                    if (medications != null) {
                        existingRecord.setMedication(medications);
                    }
                    if (allergies != null) {
                        existingRecord.setAllergies(allergies);
                    }
//                    dataLoaderService.saveData();
                    return existingRecord;
                });
    }

    // -------------------------------- DELETE ---------------------------------- //

    public boolean deleteMedicalRecord(String firstName, String lastName, String birthDate, List<String> medications, List<String> allergies) {
        log.info("Suppression du dossier médical pour {} {}", firstName, lastName);


        List<MedicalRecord> medicalRecords = Optional.ofNullable(dataLoaderService.getData().getMedicalrecords())
                .orElse(Collections.emptyList());


        boolean removed = medicalRecords.removeIf(m ->
                m.getFirstName().equalsIgnoreCase(firstName)
                        && m.getLastName().equalsIgnoreCase(lastName)  // ← equalsIgnoreCase, pas equal !
        );


        if (removed) {
//            dataLoaderService.saveData();
            log.info("Dossier médical supprimé avec succès");
        } else {
            log.warn("Aucun dossier médical trouvé pour {} {}", firstName, lastName);
        }

        return removed;
    }
}
