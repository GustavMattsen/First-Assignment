package app;

import dao.PersonDAO;
import dao.TodoItemDAO;
import dao.impl.PersonDAOJDBC;
import dao.impl.TodoItemDAOJDBC;
import model.Person;
import model.TodoItem;

import java.time.LocalDate;

public class MainJDBC {
    public static void main(String[] args) {
        // Use JDBC DAO implementations
        PersonDAO personDAO = new PersonDAOJDBC();
        TodoItemDAO todoItemDAO = new TodoItemDAOJDBC();

        //Test PersonDAO
        Person p1 = new Person(0, "Jonas", "Petterson", "jonas@gmail.com");
        personDAO.persist(p1); // INSERT into DB
        System.out.println("Saved person: " + p1);

        Person p2 = personDAO.findById(p1.getId());
        System.out.println("Found person by id: " + p2);

        //Test TodoItemDAO
        TodoItem item1 = new TodoItem(
                0,
                "Buy groceries",
                "Milk, bread, eggs",
                LocalDate.of(2025, 9, 30),
                false,
                null // no assignee in beginner version
        );
        todoItemDAO.persist(item1);
        System.out.println("Saved todo: " + item1);

        TodoItem item2 = todoItemDAO.findById(item1.getId());
        System.out.println("Found todo by id: " + item2);

        //List all persons & todos
        System.out.println("All persons:");
        for (Person p : personDAO.findAll()) {
            System.out.println(" - " + p);
        }

        System.out.println("All todos:");
        for (TodoItem t : todoItemDAO.findAll()) {
            System.out.println(" - " + t);
        }
    }
}
