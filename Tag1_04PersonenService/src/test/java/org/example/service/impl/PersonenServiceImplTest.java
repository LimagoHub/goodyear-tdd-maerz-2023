package org.example.service.impl;

import org.example.persistence.Person;
import org.example.persistence.PersonenRepository;
import org.example.service.BlacklistService;
import org.example.service.PersonenServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

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

    @Nested
    class speichernClass {
        @Test
        void speichern_parameterNull_throwsPersonenServiceException() {
            PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(null));
            assertEquals("Person darf nicht null sein", ex.getMessage());
        }

        @Nested
        @DisplayName("Tests fuer den Nachnamen")
        class nachname {
            @Test
            void speichern_nachnameNull_throwsPersonenServiceException() {
                Person illegalePerson = Person.builder().id("1").vorname("John").nachname(null).build();
                PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(illegalePerson));
                assertEquals("Nachname zu kurz", ex.getMessage());
            }

            @Test
            void speichern_nachnameTooShort_throwsPersonenServiceException() {
                Person illegalePerson = Person.builder().id("1").vorname("John").nachname("D").build();
                PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(illegalePerson));
                assertEquals("Nachname zu kurz", ex.getMessage());
            }
        }

        @Test
        void speichern_vornameNull_throwsPersonenServiceException() {
            Person illegalePerson = Person.builder().id("1").vorname(null).nachname("Doe").build();
            PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(illegalePerson));
            assertEquals("Vorname zu kurz", ex.getMessage());
        }

        @Test
        void speichern_vornameTooShort_throwsPersonenServiceException() {
            Person illegalePerson = Person.builder().id("1").vorname("J").nachname("Doe").build();
            PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(illegalePerson));
            assertEquals("Vorname zu kurz", ex.getMessage());
        }

        @Test
        void speichern_antipath_throwsPersonenServiceException() {
            Person illegalePerson = Person.builder().id("1").vorname("John").nachname("Doe").build();
            when(blacklistServiceMock.isBlacklisted(any())).thenReturn(true);
            PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(illegalePerson));
            assertEquals("Antipath", ex.getMessage());
        }

        @Test
        void speichern_unexpectedExceptionInUnderlyingService_throwsPersonenServiceException() {
            Person valid = Person.builder().id("1").vorname("John").nachname("Doe").build();
            doThrow(ArithmeticException.class).when(personenRepositoryMock).save(any());
            when(blacklistServiceMock.isBlacklisted(any())).thenReturn(false);
            PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(valid));
            assertEquals("interner Fehler", ex.getMessage());
        }

        @Test
        void speichern_happyDay_personIsPassedToRepository() throws Exception {
            final Person validPerson = Person.builder().id("1").vorname("John").nachname("Doe").build();
            doNothing().when(personenRepositoryMock).save(any()); // Kann man weg lassen
            when(blacklistServiceMock.isBlacklisted(any())).thenReturn(false);
            objectUnderTest.speichern(validPerson);
            verify(personenRepositoryMock, times(1)).save(validPerson);
        }

        @Test
        void speichern_demo_personIsPassedToRepository() throws Exception {
            final Person validPerson = Person.builder().id("1").vorname("John").nachname("Doe").build();

            doNothing().when(personenRepositoryMock).save(any()); // Kann man weg lassen
            when(blacklistServiceMock.isBlacklisted(any())).thenAnswer(p-> {

                System.out.println(((Person) p.getArgument(0)).getNachname());
                return false;
            });

            objectUnderTest.speichern(validPerson);
            verify(personenRepositoryMock, times(1)).save(validPerson);
        }

        @Test
        void speichernoverloaded_happyDay_personIsPassedToRepository() throws Exception {
            //final Person validPerson = Person.builder().id("1").vorname("John").nachname("Doe").build();
            doAnswer(invocationOnMock -> {
                Person capturedPerson = invocationOnMock.getArgument(0);
                assertNotNull(capturedPerson.getId());
                assertEquals(36, capturedPerson.getId().length());
                assertEquals("John", capturedPerson.getVorname());
                assertEquals("Doe", capturedPerson.getNachname());
                return null;
            }).when(personenRepositoryMock).save(any()); // Kann man weg lassen
            when(blacklistServiceMock.isBlacklisted(any())).thenReturn(false);

            objectUnderTest.speichern("John", "Doe");
            //verify(personenRepositoryMock, times(1)).save(validPerson);
			/*
			ArgumentCaptor<Person> peopleCaptor = ArgumentCaptor.forClass(Person.class);
			verify(mock, times(2)).doSomething(peopleCaptor.capture());

			List<Person> capturedPeople = peopleCaptor.getAllValues();
			assertEquals("John", capturedPeople.get(0).getName());
			assertEquals("Jane", capturedPeople.get(1).getName());
			*/
        }
    }
    @ParameterizedTest
    @MethodSource("providePersonsForSpeichern")
    void speichern_simplevalidation(Person p, String message) {
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(p));
        assertEquals(message, ex.getMessage());
    }
    private static Stream<Arguments> providePersonsForSpeichern() {
        return Stream.of(
                Arguments.of((Person)null, "Person darf nicht null sein"),
                Arguments.of(Person.builder().id("1").vorname("John").nachname(null).build(), "Nachname zu kurz"),
                Arguments.of(Person.builder().id("1").vorname("John").nachname("D").build(), "Nachname zu kurz"),
                Arguments.of(Person.builder().id("1").vorname(null).nachname("Doe").build(), "Vorname zu kurz"),
                Arguments.of(Person.builder().id("1").vorname("J").nachname("Doe").build(), "Vorname zu kurz")

        );
    }
}