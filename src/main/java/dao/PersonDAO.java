package dao;

import model.Person;

import java.util.Collection;

// what we can do with model.Person objects
public interface PersonDAO {
    void persist(Person person); // add a new person
    Person findById(int id); // find person by id
    Person findByEmail(String email); // find by email
    Collection<Person> findAll(); // get all people
    void remove(int id); // remove a person
}
