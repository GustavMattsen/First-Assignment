package dao;

import model.TodoItem;

import java.time.LocalDate;
import java.util.Collection;

// what we can do with model.TodoItem objects
public interface TodoItemDAO {
    void persist(TodoItem todoItem); // add new
    TodoItem findById(int id); // find by id
    Collection<TodoItem> findAll(); // all items
    Collection<TodoItem> findAllByDoneStatus(boolean done); // find done/undone
    Collection<TodoItem> findByTitleContains(String title); // find if title has text
    Collection<TodoItem> findByPersonId(int personId); // find by creator's id
    Collection<TodoItem> findByDeadlineBefore(LocalDate date); // due before date
    Collection<TodoItem> findByDeadlineAfter(LocalDate date); // due after date
    void remove(int id); // delete an item
}
