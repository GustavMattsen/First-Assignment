import java.util.ArrayList;
import java.util.Collection;

// stores Person objects in a list
public class PersonDAOCollection implements PersonDAO {
    private Collection<Person> persons = new ArrayList<>();

    // add a new person
    @Override
    public void persist(Person person) {
        persons.add(person);
    }

    // find person by ID (returns null if not found)
    @Override
    public Person findById(int id) {
        for (Person p : persons) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    // find person by email
    @Override
    public Person findByEmail(String email) {
        for (Person p : persons) {
            if (p.getEmail().equals(email)) return p;
        }
        return null;
    }

    // return all persons
    @Override
    public Collection<Person> findAll() {
        return persons;
    }

    // remove a person by ID
    @Override
    public void remove(int id) {
        Person found = findById(id);
        if (found != null) {
            persons.remove(found);
        }
    }
}
