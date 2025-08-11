package model;

import java.time.LocalDate;

public class TodoItem {
    private int id;
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean done;
    private Person creator;

    // Constructor - make a todo item
    public TodoItem(int id, String title, String description, LocalDate deadLine, boolean done, Person creator) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (deadLine == null) {
            throw new IllegalArgumentException("Deadline cannot be null");
        }
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = done;
        this.creator = creator;
    }

    // Getters and Setters for each variable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        if (deadLine == null) {
            throw new IllegalArgumentException("Deadline cannot be null");
        }
        this.deadLine = deadLine;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    // Check if the deadline is already gone
    public boolean isOverdue() {
        return LocalDate.now().isAfter(deadLine);
    }


    // new toString() - turns the object into a text
    // leave out the model.Person "creator" so it doesn't print all their details
    @Override
    public String toString() {
        return "model.TodoItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadLine=" + deadLine +
                ", done=" + done +
                '}';
    }

    // this checks if two model.TodoItem objects are equal
    // I don't compare the creator here, just the basic fields
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TodoItem)) return false;
        TodoItem todoItem = (TodoItem) o;
        return id == todoItem.id &&
                done == todoItem.done &&
                java.util.Objects.equals(title, todoItem.title) &&
                java.util.Objects.equals(description, todoItem.description) &&
                java.util.Objects.equals(deadLine, todoItem.deadLine);
    }


    // makes a number from the fields (used for hash tables and stuff)
    // creator not included
    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, title, description, deadLine, done);
    }
}
