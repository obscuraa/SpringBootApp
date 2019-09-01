package com.examplegroup.exampleArtifact.DAO;

import com.examplegroup.exampleArtifact.Model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("postgres")
public class PostgresDataAccessService implements PersonDAO {

    private final JdbcTemplate jdbct;

    @Autowired
    public PostgresDataAccessService(JdbcTemplate jdbct) {
        this.jdbct = jdbct;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        String sql_query = "INSERT INTO person (" + " id, " + " name" + "VALUES (?, ?)";
        return jdbct.update(sql_query, id);
    }

    @Override
    public List<Person> selectAllPeople() {
        //JDBC
        String sql_query = "SELECT id, name FROM person";
        return jdbct.query(sql_query, (resultSet, i) ->  {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(id, name);
        });

        // TestPerson
        // Java 8
        //return Arrays.asList(new Person(UUID.randomUUID(), "FROM POSTGRES DB"));
        // java 11
        //List.of(new Person(UUID.randomUUID(), "From Postgres DB"));
    }

    @Override
    public int deletePersonByID(UUID id) {
        String sql_query = "" + "DELETE FROM person " + "WHERE id = ?";
        return jdbct.update(sql_query, id);
    }

    @Override
    public int updatePersonByID(UUID id, Person person) {
        String sql_query = "UPDATE student " + "SET first_name = ? " + "WHERE student_id = ?";
        return jdbct.update(sql_query, person.getName(), id);
    }

    @Override
    public Optional<Person> selectPersonByID(UUID id) {
        String sql_query = "SELECT id, name FROM person WHERE id = ?";
        Person person = jdbct.queryForObject(sql_query, new Object[]{id}, (resultSet, i) ->  {
            UUID personid = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(personid, name);
        });
        return Optional.ofNullable(person);
    }
}
