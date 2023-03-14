package org.example.service.impl;

import org.example.persistence.Person;
import org.example.persistence.PersonenRepository;
import org.example.service.BlacklistService;
import org.example.service.PersonenServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PersonenServiceImplTest {

    @InjectMocks
    private PersonenServiceImpl objectUnderTest;

    @Mock
    private PersonenRepository personenRepositoryMock;

    @Mock
    private BlacklistService blacklistServiceMock;

    @Test
    void speichern_parameterNull_throwsPersonenServiceException(){
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(null));
        assertEquals("Person darf nicht null sein", ex.getMessage());
    }
    @Test
    void speichern_nachnameNull_throwsPersonenServiceException(){
        Person illegalePerson = Person.builder().id("1").vorname("John").nachname(null).build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(illegalePerson));
        assertEquals("Nachname zu kurz", ex.getMessage());
    }

    @Test
    void speichern_nachnameTooShort_throwsPersonenServiceException(){
        Person illegalePerson = Person.builder().id("1").vorname("John").nachname("D").build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(illegalePerson));
        assertEquals("Nachname zu kurz", ex.getMessage());
    }
    @Test
    void speichern_vornameNull_throwsPersonenServiceException(){
        Person illegalePerson = Person.builder().id("1").vorname(null).nachname("Doe").build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(illegalePerson));
        assertEquals("Vorname zu kurz", ex.getMessage());
    }

    @Test
    void speichern_vornameTooShort_throwsPersonenServiceException(){
        Person illegalePerson = Person.builder().id("1").vorname("J").nachname("Doe").build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(illegalePerson));
        assertEquals("Vorname zu kurz", ex.getMessage());
    }

    @Test
    void speichern_antipath_throwsPersonenServiceException(){
        Person illegalePerson = Person.builder().id("1").vorname("John").nachname("Doe").build();
        when(blacklistServiceMock.isBlacklisted(any())).thenReturn(true);
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(illegalePerson));
        assertEquals("Antipath", ex.getMessage());
    }

    @Test
    void speichern_unexpectedExceptionInUnderlyingService_throwsPersonenServiceException(){
        Person valid = Person.builder().id("1").vorname("John").nachname("Doe").build();
        doThrow(ArithmeticException.class).when(personenRepositoryMock).save(any());
        when(blacklistServiceMock.isBlacklisted(any())).thenReturn(false);
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(valid));
        assertEquals("interner Fehler", ex.getMessage());
    }

    @Test
    void speichern_happyDay_personIsPassedToRepository() throws Exception{
        final Person validPerson = Person.builder().id("1").vorname("John").nachname("Doe").build();
        doNothing().when(personenRepositoryMock).save(any()); // Kann man weg lassen
        when(blacklistServiceMock.isBlacklisted(any())).thenReturn(false);
        objectUnderTest.speichern(validPerson);
        verify(personenRepositoryMock, times(1)).save(validPerson);
    }

}