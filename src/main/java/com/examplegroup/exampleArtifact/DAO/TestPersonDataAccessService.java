package com.examplegroup.exampleArtifact.DAO;

import com.examplegroup.exampleArtifact.Model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class TestPersonDataAccessService implements PersonDAO {

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople(){
        return DB;
    }

    @Override
    public int deletePersonByID(UUID id) {
        Optional<Person> personDel = selectPersonByID(id);
        if (!personDel.isPresent()){
            return 0;
        }
        DB.remove(personDel.get());
        return 1;
    }

    @Override
    public int updatePersonByID(UUID id, Person update) {
        return selectPersonByID(id).map(person -> {
            int indexOfPersonToUpdate = DB.indexOf(person);
            if(indexOfPersonToUpdate >=0){
                DB.set(indexOfPersonToUpdate, new Person(id, update.getName()));
                return 1;
            }
            return 0;
        }).orElse(0);
    }

    @Override
    public Optional<Person> selectPersonByID(UUID id) {
        return DB.stream().filter(person -> person.getID().equals(id)).findFirst();
    }


}
