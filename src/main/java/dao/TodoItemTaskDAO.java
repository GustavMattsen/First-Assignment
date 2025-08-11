package dao;

import model.TodoItemTask;

import java.util.Collection;

// what we can do with model.TodoItemTask objects
public interface TodoItemTaskDAO {
    void persist(TodoItemTask task); // add new
    TodoItemTask findById(int id); // find by id
    Collection<TodoItemTask> findAll(); // all tasks
    Collection<TodoItemTask> findByAssignedStatus(boolean assigned); // assigned/unassigned
    Collection<TodoItemTask> findByPersonId(int personId); // tasks for a person
    void remove(int id); // delete a task
}
