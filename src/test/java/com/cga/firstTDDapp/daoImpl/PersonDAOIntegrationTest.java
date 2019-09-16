package com.cga.firstTDDapp.daoImpl;

import com.cga.firstTDDapp.dao.PersonDao;
import com.cga.firstTDDapp.model.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;


import javax.sql.DataSource;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PersonDAOIntegrationTest {

    private PersonDao personDao;
    private DataSource dataSource;

    @Before
    public void setUp() throws Exception {
        dataSource = new DriverManagerDataSource("jdbc:h2:mem:test;MODE=MYSQL","cga","cga");

        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("sql-scripts/test-db.sql"));

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        personDao = new PersonDAOImpl(jdbcTemplate);
    }


    @Test
    public void getAllPersons() {
        List<Person> allPersons = personDao.findAllPersons();
        assertThat(allPersons, is(Arrays.asList(
                new Person((long) 1,"John","Connor"),
                new Person((long) 2,"Sarah","Connor")
        )));
    }

    @Test
    public void getPersonById() {
        Person actualPerson = personDao.findPersonById((long) 2);
        assertThat(actualPerson, is(
                new Person((long) 2,"Sarah","Connor")
        ));

    }

    @Test
    public void getPersonByName_with_insensitive_case() {
        Person actualPerson = personDao.findPersonByName("sar");
        assertThat(actualPerson, is(
                new Person((long) 2,"Sarah","Connor")
        ));
    }

    @Test
    public void save_person() {
        personDao.savePerson(new Person("Kyle","Reese"));
        List<Person> allPersons = personDao.findAllPersons();
        assertThat(allPersons, is(Arrays.asList(
                new Person((long) 1,"John","Connor"),
                new Person((long) 2,"Sarah","Connor"),
                new Person((long) 3,"Kyle","Reese")
        )));
    }

    @Test
    public void delete_person() {
        personDao.deletePersonById((long) 2);
        List<Person> allPersons = personDao.findAllPersons();
        assertThat(allPersons, is(Arrays.asList(
                new Person((long) 1,"John","Connor")
        )));
    }

    @Test
    public void update_person() {
        personDao.updatePerson(new Person((long) 2,"Kyle","Reese"));
        List<Person> allPersons = personDao.findAllPersons();
        assertThat(allPersons, is(Arrays.asList(
                new Person((long) 1,"John","Connor"),
                new Person((long) 2,"Kyle","Reese")
        )));
    }

    @After
    public void tearDown() throws Exception {
        String query = "drop all objects delete files";
        final Statement s = dataSource.getConnection().createStatement();
        s.execute(query);
    }
}