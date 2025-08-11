package app;

import dao.impl.AppUserDAOCollection;
import dao.impl.PersonDAOCollection;
import dao.impl.TodoItemDAOCollection;
import dao.impl.TodoItemTaskDAOCollection;
import model.*;

import sequencer.PersonSequencer;
import sequencer.TodoItemSequencer;
import sequencer.TodoItemTaskSequencer;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Create DAO instances
        AppUserDAOCollection appUserDAO = new AppUserDAOCollection();
        PersonDAOCollection personDAO = new PersonDAOCollection();
        TodoItemDAOCollection todoItemDAO = new TodoItemDAOCollection();
        TodoItemTaskDAOCollection taskDAO = new TodoItemTaskDAOCollection();

        // Create some AppUsers
        AppUser user1 = new AppUser("jonas123", "pass1", AppRole.ROLE_APP_USER);
        AppUser user2 = new AppUser("admin1", "adminpass", AppRole.ROLE_APP_ADMIN);

        // save to DAO
        appUserDAO.persist(user1);
        appUserDAO.persist(user2);

        // Create Persons
        Person p1 = new Person(PersonSequencer.nextId(), "Jonas", "Petterson", "jonas@mail.com");
        p1.setCredentials(user1); // link login info
        Person p2 = new Person(PersonSequencer.nextId(), "Admin", "Boss", "admin@mail.com");
        p2.setCredentials(user2);

        personDAO.persist(p1);
        personDAO.persist(p2);

        // Create TodoItems
        TodoItem item1 = new TodoItem(TodoItemSequencer.nextId(), "Buy groceries", "Milk, bread, eggs",
                LocalDate.of(2025, 8, 20), false, p1);
        TodoItem item2 = new TodoItem(TodoItemSequencer.nextId(), "Pay bills", "Electricity and water",
                LocalDate.of(2025, 8, 15), false, p1);

        todoItemDAO.persist(item1);
        todoItemDAO.persist(item2);

        // Create TodoItemTasks
        TodoItemTask task1 = new TodoItemTask(TodoItemTaskSequencer.nextId(), item1, p1);
        TodoItemTask task2 = new TodoItemTask(TodoItemTaskSequencer.nextId(), item2, p2);

        taskDAO.persist(task1);
        taskDAO.persist(task2);

        // Testing DAO methods
        System.out.println("Find user by username: " + appUserDAO.findByUsername("jonas123"));
        System.out.println("Find person by ID: " + personDAO.findById(1));
        System.out.println("Find todo by ID: " + todoItemDAO.findById(1));
        System.out.println("Find tasks assigned to p1: " + taskDAO.findByPersonId(p1.getId()));

        // Print all data
        System.out.println("\nAll users: " + appUserDAO.findAll());
        System.out.println("All persons: " + personDAO.findAll());
        System.out.println("All todo items: " + todoItemDAO.findAll());
        System.out.println("All tasks: " + taskDAO.findAll());
    }
}
