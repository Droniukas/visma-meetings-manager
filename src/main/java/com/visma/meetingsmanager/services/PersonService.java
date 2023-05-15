package com.visma.meetingsmanager.services;

import com.visma.meetingsmanager.models.Person;
import com.visma.meetingsmanager.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getPeopleByNames(List<String> names) {
        List<Person> people = personRepository.getAll();
        List<Person> outputPeople = new ArrayList<>();
        names.forEach(name -> outputPeople.add(people.stream().filter(person -> person.getName().equals(name)).findFirst().orElse(null)));
        return outputPeople;
    }

    public Person getPersonByName(String name) {
        List<Person> people = personRepository.getAll();
        return people.stream().filter(person -> person.getName().equals(name)).findFirst().orElse(null);
    }
}
