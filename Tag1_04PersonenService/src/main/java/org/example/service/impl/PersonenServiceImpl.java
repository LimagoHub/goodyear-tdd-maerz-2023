package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.persistence.Person;
import org.example.persistence.PersonenRepository;
import org.example.service.BlacklistService;
import org.example.service.PersonenService;
import org.example.service.PersonenServiceException;

import java.util.UUID;


@RequiredArgsConstructor
public class PersonenServiceImpl implements PersonenService {

    //Dependency-Injection oder Inversion of Control
    private final PersonenRepository repo;
    private final BlacklistService blacklistService;

    /*
        1.) wenn person = null => PSE
        2.) wenn vorname null oder weniger als 2 Zeichen => PSE
        3.) wenn nachname null oder weniger als 2 Zeichen => PSE
        4.) wenn Vorname = Attila => PSE
        5.) wenn Laufzeitfehler => PSE

        Happy day => person via repo speichern
     */
    @Override
    public void speichern(Person person) throws PersonenServiceException {
        try {
            checkPerson(person);
            person.setId(UUID.randomUUID().toString());
            repo.save(person);
        } catch (RuntimeException e) {
            throw new PersonenServiceException("interner Fehler", e);
        }
    }

    @Override
    public void speichern(String vorname, String nachname) throws PersonenServiceException {
        Person p= Person.builder().id(UUID.randomUUID().toString()).vorname(vorname).nachname(nachname).build();
        speichern(p);
    }

    private void checkPerson(final Person person) throws PersonenServiceException {
        validate(person);
        businessCheck(person);
    }

    private void businessCheck(Person person) throws PersonenServiceException {
        if(blacklistService.isBlacklisted(person))
            throw new PersonenServiceException("Antipath");
    }

    private static void validate(Person person) throws PersonenServiceException {
        if(person == null)
            throw new PersonenServiceException("Person darf nicht null sein");
        if(person.getVorname() == null || person.getVorname().length() < 2)
            throw new PersonenServiceException("Vorname zu kurz");

        if(person.getNachname() == null || person.getNachname().length() < 2)
            throw new PersonenServiceException("Nachname zu kurz");
    }
}
