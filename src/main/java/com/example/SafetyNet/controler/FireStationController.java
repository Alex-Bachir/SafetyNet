package com.example.SafetyNet.controler;

import com.example.SafetyNet.model.FireStation;
import com.example.SafetyNet.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private FireStationService fireStationService;
    @Autowired
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping
    public List<FireStation> getFireStations() {
        return fireStationService.getAllFireStations();
    }

    @GetMapping("/{address}/{station}")
    public ResponseEntity<FireStation> getFireStationByadress(@PathVariable String address, @PathVariable Integer station, @RequestBody FireStation fireStation) {
        try {
            FireStation getget = fireStationService.updateFireStation(address, station, fireStation);
            return ResponseEntity.ok(getget);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{address}/{station}")
    public ResponseEntity<FireStation> deleteFireStation(@PathVariable String address, @PathVariable Integer station) {
        boolean deleted = fireStationService.deleteFireStation(address, station);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();

    }
}
