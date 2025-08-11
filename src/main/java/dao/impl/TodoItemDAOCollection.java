package dao.impl;

import dao.TodoItemDAO;
import model.TodoItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

// stores model.TodoItem objects in memory
public class TodoItemDAOCollection implements TodoItemDAO {
    private Collection<TodoItem> items = new ArrayList<>();

    // add a new todo item
    @Override
    public void persist(TodoItem todoItem) {
        items.add(todoItem);
    }

    // find an item by its ID
    @Override
    public TodoItem findById(int id) {
        for (TodoItem t : items) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    // return all todo items
    @Override
    public Collection<TodoItem> findAll() {
        return items;
    }

    // find all items with the "done" status
    @Override
    public Collection<TodoItem> findAllByDoneStatus(boolean done) {
        Collection<TodoItem> result = new ArrayList<>();
        for (TodoItem t : items) {
            if (t.isDone() == done) result.add(t);
        }
        return result;
    }

    // find all items that have the given text inside the title
    @Override
    public Collection<TodoItem> findByTitleContains(String title) {
        Collection<TodoItem> result = new ArrayList<>();
        for (TodoItem t : items) {
            if (t.getTitle().contains(title)) result.add(t);
        }
        return result;
    }

    // find all items created by a specific person
    @Override
    public Collection<TodoItem> findByPersonId(int personId) {
        Collection<TodoItem> result = new ArrayList<>();
        for (TodoItem t : items) {
            if (t.getCreator() != null && t.getCreator().getId() == personId) {
                result.add(t);
            }
        }
        return result;
    }

    // find all items with a deadline before the given date
    @Override
    public Collection<TodoItem> findByDeadlineBefore(LocalDate date) {
        Collection<TodoItem> result = new ArrayList<>();
        for (TodoItem t : items) {
            if (t.getDeadLine().isBefore(date)) result.add(t);
        }
        return result;
    }

    // same as the one above except after the given date
    @Override
    public Collection<TodoItem> findByDeadlineAfter(LocalDate date) {
        Collection<TodoItem> result = new ArrayList<>();
        for (TodoItem t : items) {
            if (t.getDeadLine().isAfter(date)) result.add(t);
        }
        return result;
    }

    // remove an item by ID
    @Override
    public void remove(int id) {
        TodoItem found = findById(id);
        if (found != null) {
            items.remove(found);
        }
    }
}