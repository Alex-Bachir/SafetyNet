package com.example.SafetyNet.service;

import com.example.SafetyNet.model.SafetyNetData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class DataLoaderService {

    private SafetyNetData safetyNetData;
    private final ObjectMapper objectMapper;

    public DataLoaderService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadData() {
        try {
            log.info("Chargement des données depuis data.json...");

            ClassPathResource resource = new ClassPathResource("data.json");
            InputStream inputStream = resource.getInputStream();

            safetyNetData = objectMapper.readValue(inputStream, SafetyNetData.class);

            log.info(" Données chargées avec succès !");
            log.info("   - Personnes : {}", safetyNetData.getPersons().size());
            log.info("   - Casernes : {}", safetyNetData.getFirestations().size());
            log.info("   - Dossiers médicaux : {}", safetyNetData.getMedicalrecords().size());

        } catch (IOException e) {
            log.error("Erreur lors du chargement du fichier JSON", e);
            throw new RuntimeException("Impossible de charger les données", e);
        }
    }

    public void saveData() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("data.json"), safetyNetData);
            System.out.println("Données sauvegardées dans le fichier JSON");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public SafetyNetData getData() {
        return safetyNetData;
    }
}