package com.example.SafetyNet.service;

import com.example.SafetyNet.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {


    private final DataLoaderService dataLoaderService;

    public PersonService(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
    }

    public List<Person> getAllPersons() {
        return dataLoaderService.getData().getPersons();
    }

    public Optional<Person> findByName(String name, String lastName) {
        return Optional.ofNullable(dataLoaderService.getData().getPersons())
                .orElse(Collections.emptyList())
                .stream()
                .filter(person -> person.getFirstName().equals(name) &&
                        person.getLastName().equals(lastName))
                .findFirst();
    }

    public List<Person> findByAddress(String address) {
        return Optional.ofNullable(dataLoaderService.getData().getPersons())
                .orElse(Collections.emptyList())
                .stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    // ------------------------------ POST -------------------------------------------- //

    public Person addPerson(Person person) {
        List<Person> persons = Optional.ofNullable(dataLoaderService.getData().getPersons())
                .orElseGet(() -> {
                    List<Person> newList = new ArrayList<>();
                    dataLoaderService.getData().setPersons(newList);
                    return newList;
                });

        persons.add(person);
//        dataLoaderService.saveData();

        return person;
    }

    // --------------------------------- UPDATE ----------------------------------- //

    public Person updatePerson(String name, String lastName, Person updatetedPerson) {

        Optional<Person> existingPerson = findByName(name, lastName);
        if (existingPerson.isEmpty()) {
            throw new IllegalArgumentException("Personne introuvable");
        }
        Person person = existingPerson.get();
        person.setAddress(updatetedPerson.getAddress());
        person.setCity(updatetedPerson.getCity());
        person.setZip(updatetedPerson.getZip());
        person.setPhone(updatetedPerson.getPhone());
        person.setEmail(updatetedPerson.getEmail());

        return person;
    }


    // ---------------------------------- DELETE ----------------------------------------------//

    public boolean deletePerson(String firstName, String lastName) {

        boolean removed = dataLoaderService.getData().getPersons()
                .removeIf(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName));


        return removed;
    }

    public long countPersons() {
        return dataLoaderService.getData().getPersons().size();
    }
}
