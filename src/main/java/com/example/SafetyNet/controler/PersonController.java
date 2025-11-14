package com.example.SafetyNet.controler;

import com.example.SafetyNet.model.Person;
import com.example.SafetyNet.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{firstName}/{lastName}")
    public ResponseEntity<Person> getByName(@PathVariable String firstName,
                                            @PathVariable String lastName) {
        return personService.findByName(firstName, lastName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        try {
            Person addedPerson = personService.addPerson(person);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedPerson);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{firstName}/{lastName}")
    public ResponseEntity<Person> updatePerson(@PathVariable String firstName,
                                               @PathVariable String lastName,
                                               @RequestBody Person person) {
        try {
            Person updated = personService.updatePerson(firstName, lastName, person);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(@PathVariable String firstName,
                                             @PathVariable String lastName) {
        boolean deleted = personService.deletePerson(firstName, lastName);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
