package com.visma.meetingsmanager.services;

import com.visma.meetingsmanager.models.Person;
import com.visma.meetingsmanager.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService underTest;

    @Test
    void getPeopleByNames() {
        Person firstPerson = new Person("Tadas", 22);
        Person secondPerson = new Person("Vilius", 25);
        when(personRepository.getAll())
                .thenReturn(List.of(firstPerson, secondPerson));
    }

    @Test
    void getPersonByName() {
        Person firstPerson = new Person("Tadas", 22);
        Person secondPerson = new Person("Vilius", 25);
        when(personRepository.getAll())
                .thenReturn(List.of(firstPerson, secondPerson));

        Person actualFirstPerson = underTest.getPersonByName("Tadas");
        assertThat(firstPerson).isEqualTo(actualFirstPerson);
    }
}