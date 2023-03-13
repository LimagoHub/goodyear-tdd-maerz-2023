package org.example.service.impl;

import org.example.persistence.Person;
import org.example.persistence.PersonenRepository;
import org.example.service.PersonenServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class PersonenServiceImplTest {

    @InjectMocks
    private PersonenServiceImpl objectUnderTest;

    @Mock
    private PersonenRepository personenRepositoryMock;

    @Test
    void speichern_parameterNull_throwsPersonenServiceException(){
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(null));
        assertEquals("Person darf nicht null sein", ex.getMessage());
    }
    @Test
    void speichern_vornameNull_throwsPersonenServiceException(){
        Person illegalePerson = Person.builder().id("1").vorname(null).nachname("Doe").build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(illegalePerson));
        assertEquals("Vorname zu kurz", ex.getMessage());
    }

    @Test
    void speichern_vornameToShort_throwsPersonenServiceException(){
        Person illegalePerson = Person.builder().id("1").vorname("J").nachname("Doe").build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(illegalePerson));
        assertEquals("Vorname zu kurz", ex.getMessage());
    }

}