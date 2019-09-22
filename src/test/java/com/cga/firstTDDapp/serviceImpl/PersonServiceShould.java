package com.cga.firstTDDapp.serviceImpl;

import com.cga.firstTDDapp.dao.PersonDao;
import com.cga.firstTDDapp.model.Person;
import com.cga.firstTDDapp.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;

public class PersonServiceShould {
    private PersonService personService;
    private ArgumentCaptor valueCapture;
    PersonDao personDao;

    @Before
    public void setUp() throws Exception {
        personDao = Mockito.mock(PersonDao.class);
        personService = new PersonServiceImpl(personDao);
        Mockito.when(personDao.findAllPersons()).thenReturn(Arrays.asList(
                new Person((long) 1,"John","Connor"),
                new Person((long) 2,"Sarah","Connor"),
                new Person((long) 3,"Kyle","Reese")
        ));
        Mockito.when(personDao.findPersonById(anyLong())).thenReturn(
                new Person((long) 1,"John","Connor")
        );
        Mockito.when(personDao.findPersonByName("sar")).thenReturn(
                new Person((long) 2,"Sarah","Connor")
        );
    }

    @Test
    public void find_all_persons() {

        Collection<Person> persons = personService.findAllPersons();
        assertThat(persons.size(), is(3));
    }

    @Test
    public void find_person_by_id() {
        Person actualPerson = new Person((long) 1,"John","Connor");
        Person expectedPerson = personService.findPersonById((long) 1);

        assertThat(actualPerson, is(expectedPerson));
    }

    @Test
    public void find_person_by_name_ignore_case() {
        Person actualPerson = new Person((long) 2,"Sarah","Connor");
        Person expectedPerson = personService.findPersonByName("sar");

        assertThat(actualPerson, is(expectedPerson));
    }

  /*  @Test
    public void save_person_argument_test() {
        Person actualPerson = new Person((long) 3,"Kyle","Reese");
        valueCapture = ArgumentCaptor.forClass(Person.class);
        PersonDao personDao = Mockito.mock(PersonDao.class);
        personService = Mockito.spy(new PersonServiceImpl(personDao));

        doNothing().when(personService).savePerson((Person)valueCapture.capture());
        personService.savePerson(actualPerson);

        assertThat(actualPerson, is(valueCapture.getValue()));

    }*/

    @Test
    public void save_person_verify_dao_call() {
        Person actualPerson = new Person((long) 3,"Kyle","Reese");
        valueCapture = ArgumentCaptor.forClass(Person.class);
        personService = Mockito.spy(new PersonServiceImpl(personDao));

        doNothing().when(personDao).savePerson((Person)valueCapture.capture());
        personService.savePerson(actualPerson);

        assertThat(actualPerson, is(valueCapture.getValue()));

    }

    @Test
    public void delete_person_by_id() {

        valueCapture = ArgumentCaptor.forClass(Long.class);
        personService = Mockito.spy(new PersonServiceImpl(personDao));

        doNothing().when(personDao).deletePersonById((long)valueCapture.capture());
        personService.deletePersonById((long) 1);

        assertThat((long) 1, is(valueCapture.getValue()));
    }

    @Test
    public void update_person_verify_dao_call() {
        Person actualPerson = new Person((long) 3,"Kyle","Reese");
        valueCapture = ArgumentCaptor.forClass(Person.class);
        personService = Mockito.spy(new PersonServiceImpl(personDao));

        doNothing().when(personDao).updatePerson((Person)valueCapture.capture());
        personService.updatePerson(actualPerson);

        assertThat(actualPerson, is(valueCapture.getValue()));

    }
}