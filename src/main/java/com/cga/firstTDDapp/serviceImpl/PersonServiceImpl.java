package com.cga.firstTDDapp.serviceImpl;

import com.cga.firstTDDapp.dao.PersonDao;
import com.cga.firstTDDapp.model.Person;
import com.cga.firstTDDapp.service.PersonService;

import java.util.List;

public class PersonServiceImpl implements PersonService {

    private PersonDao personDao;

    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public List<Person> findAllPersons() {
        return personDao.findAllPersons();
    }

    @Override
    public Person findPersonById(Long id) {
        return personDao.findPersonById(id);
    }

    @Override
    public Person findPersonByName(String name) {
        return personDao.findPersonByName(name);
    }

    @Override
    public void savePerson(Person person) {
        personDao.savePerson(person);
    }

    @Override
    public void deletePersonById(Long id) {
        personDao.deletePersonById(id);
    }

    @Override
    public void updatePerson(Person person) {
        personDao.updatePerson(person);
    }
}
