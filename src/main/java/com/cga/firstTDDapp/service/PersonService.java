package com.cga.firstTDDapp.service;

import com.cga.firstTDDapp.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAllPersons();
    Person findPersonById(Long id);
    Person findPersonByName(String name);
    void savePerson(Person person);
    void deletePersonById(Long id);
    void updatePerson(Person person);
}
