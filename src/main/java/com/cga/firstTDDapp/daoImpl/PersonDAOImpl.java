package com.cga.firstTDDapp.daoImpl;

import com.cga.firstTDDapp.dao.PersonDao;
import com.cga.firstTDDapp.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class PersonDAOImpl implements PersonDao {

    private JdbcTemplate jdbcTemplate;

    PersonDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> findAllPersons() {
        return jdbcTemplate.query("select * from Persons", personMapper);
    }

    @Override
    public Person findPersonById(Long id) {
        Object[] params = {id};
        return jdbcTemplate.queryForObject("select * from persons where id = ?",params,personMapper);
    }


    @Override
    public Person findPersonByName(String name) {
        Object[] params = {"%"+name+"%", "%"+name+"%"};
        return jdbcTemplate.queryForObject("select * from persons where (LOWER(firstName) LIKE LOWER(?)) OR (LOWER(lastName) LIKE LOWER(?))",params,personMapper);
    }

    @Override
    public void savePerson(Person person) {
        jdbcTemplate.update("insert into persons (firstName,lastName) values(?,?)",person.getFirstName(),person.getLastName());
    }

    @Override
    public void deletePersonById(Long id) {
        jdbcTemplate.update("delete from persons where id = ?", id);
    }

    @Override
    public void updatePerson(Person person) {
        jdbcTemplate.update("update Persons set firstName = ?, lastName = ? where id = ?", person.getFirstName(), person.getLastName(), person.getId());
    }

    private static RowMapper<Person> personMapper = (rs, rowNum) -> new Person(
            (long) rs.getInt("id"),
            rs.getString("firstName"),
            rs.getString("lastName"));
}
