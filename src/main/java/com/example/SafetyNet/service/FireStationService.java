package com.example.SafetyNet.service;

import com.example.SafetyNet.model.FireStation;
import com.example.SafetyNet.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@Slf4j
public class FireStationService {

    private final DataLoaderService dataLoaderService;

    public FireStationService(DataLoaderService dataLoaderService) {

        this.dataLoaderService = dataLoaderService;
    }

    public List<FireStation> getAllFireStations() {

        return dataLoaderService.getData().getFirestations();
    }


    public Optional<FireStation> findByAddress(String address) {
        return Optional.ofNullable(dataLoaderService.getData().getFirestations())
                .orElse(Collections.emptyList())
                .stream()
                .filter(fireStation -> address.equals(fireStation.getAddress()))
                .findFirst();
    }


    public List<FireStation> findByStation(Integer station) {
        return Optional.ofNullable(dataLoaderService.getData().getFirestations())
                .orElse(Collections.emptyList())
                .stream()
                .filter(fireStation -> station.equals(fireStation.getStation()))
                .collect(Collectors.toList());
    }

    // ------------------------------------ POST ----------------------------------------- //

    public FireStation addFireStation(FireStation fireStation) {
        List<FireStation> fireStations = Optional.ofNullable(dataLoaderService.getData().getFirestations())
                .orElseGet(() -> {
                    List<FireStation> fireStationList = new ArrayList<>();
                    dataLoaderService.getData().setFirestations(fireStationList);
                    return fireStationList;
                });

        fireStations.add(fireStation);

        dataLoaderService.saveData();

        return fireStation;

    }
    // --------------------------- UPDATE ------------------------------------- //

    public FireStation updateFireStation(String address, Integer station, FireStation updatedFireStation) {
        Optional<FireStation> existingFireStation = findByAddress(address);
        if (existingFireStation.isEmpty()) {
            throw new RuntimeException("adresse ou station introuvable");
        }
        FireStation fireStation = existingFireStation.get();
        fireStation.setAddress(updatedFireStation.getAddress());
        fireStation.setStation(updatedFireStation.getStation());

        dataLoaderService.saveData();

        return fireStation;
    }

    // ----------------------------------- DELETE ------------------------------------------ //

    public boolean deleteFireStation(String address, Integer station) {
        boolean removed = dataLoaderService.getData().getFirestations()
                .removeIf(f -> f.getAddress().equalsIgnoreCase(address)
                        && f.getStation().equals(station));

        if (removed) {
            dataLoaderService.saveData();
        }

        dataLoaderService.saveData();

        return removed;
    }

//    public long countFireStations() {
//        List<FireStation> fireStations = dataLoaderService.getData().getFirestations();
//        return fireStations != null ? fireStations.size() : 0;
//    }

}

