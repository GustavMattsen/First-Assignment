package dao.impl;

import dao.PersonDAO;
import model.Person;
import database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class PersonDAOJDBC implements PersonDAO {

    @Override
    public void persist(Person person) {
        // insert a new row into the person table
        String sql = "INSERT INTO person (first_name, last_name, email) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // set values for the placeholders
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setString(3, person.getEmail());
            // run the insert
            ps.executeUpdate();
            // after insert we get the generated id back
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    person.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            // just wrap in runtime exception so it crashes if db fails
            throw new RuntimeException(e);
        }
    }

    @Override
    public Person findById(int id) {
        // look up a single person by its id
        String sql = "SELECT person_id, first_name, last_name, email FROM person WHERE person_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id); // set the id into the query
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // build a Person object from the row
                    return new Person(
                            rs.getInt("person_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // nothing found
    }

    @Override
    public Person findByEmail(String email) {
        // search for a person by email
        String sql = "SELECT person_id, first_name, last_name, email FROM person WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Person(
                            rs.getInt("person_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Collection<Person> findAll() {
        // get all rows from person table
        String sql = "SELECT person_id, first_name, last_name, email FROM person";
        Collection<Person> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // for each row create a Person object
                result.add(new Person(
                        rs.getInt("person_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void remove(int id) {
        // delete a person by id
        String sql = "DELETE FROM person WHERE person_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id); // set the id
            ps.executeUpdate(); // run delete
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
