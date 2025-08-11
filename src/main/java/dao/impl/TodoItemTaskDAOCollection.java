package dao.impl;

import dao.TodoItemTaskDAO;
import model.TodoItemTask;
import model.Person;

import java.util.ArrayList;
import java.util.Collection;

// This stores TodoItemTask objects
// A TodoItemTask connects a TodoItem with the person assigned to do it
public class TodoItemTaskDAOCollection implements TodoItemTaskDAO {

    private Collection<TodoItemTask> tasks = new ArrayList<>();

    // add a new task
    @Override
    public void persist(TodoItemTask task) {
        tasks.add(task);
    }

    // find a task by ID
    @Override
    public TodoItemTask findById(int id) {
        for (TodoItemTask t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    // return all tasks
    @Override
    public Collection<TodoItemTask> findAll() {
        return tasks;
    }

    // find all tasks that are assigned/unassigned
    @Override
    public Collection<TodoItemTask> findByAssignedStatus(boolean assigned) {
        Collection<TodoItemTask> result = new ArrayList<>();
        for (TodoItemTask t : tasks) {
            if (t.isAssigned() == assigned) {
                result.add(t);
            }
        }
        return result;
    }

    // find all tasks assigned to a specific person
    @Override
    public Collection<TodoItemTask> findByPersonId(int personId) {
        Collection<TodoItemTask> result = new ArrayList<>();
        for (TodoItemTask t : tasks) {
            if (t.getAssignee() != null && t.getAssignee().getId() == personId) {
                result.add(t);
            }
        }
        return result;
    }

    // remove a task by ID
    @Override
    public void remove(int id) {
        TodoItemTask found = findById(id);
        if (found != null) {
            tasks.remove(found);
        }
    }
}
