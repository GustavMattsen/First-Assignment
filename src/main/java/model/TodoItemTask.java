package model;

public class TodoItemTask {
    private int id;
    private boolean assigned; // true, if we have someone to do it
    private TodoItem todoItem; // what needs to be done
    private Person assignee;   // person who will do it

    // Constructor
    public TodoItemTask(int id, TodoItem todoItem, Person assignee) {
        if (todoItem == null) {
            throw new IllegalArgumentException("model.TodoItem cannot be null");
        }
        this.id = id;
        this.todoItem = todoItem;
        this.assignee = assignee;
        this.assigned = (assignee != null); // if we gave a person, assigned = true
    }

    // Basic getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssignee(Person assignee) {
        this.assignee = assignee;
        this.assigned = (assignee != null);
    }

    public TodoItem getTodoItem() {
        return todoItem;
    }

    public void setTodoItem(TodoItem todoItem) {
        if (todoItem == null) {
            throw new IllegalArgumentException("model.TodoItem cannot be null");
        }
        this.todoItem = todoItem;
    }

    public Person getAssignee() {
        return assignee;
    }


    // new toString() - gives a quick text of the task
    // assignee (model.Person) is left out so it doesn't print their info
    @Override
    public String toString() {
        return "model.TodoItemTask{" +
                "id=" + id +
                ", assigned=" + assigned +
                ", todoItem=" + todoItem +
                '}';
    }

    // checks if two model.TodoItemTask objects are "equal"
    // model.Person (assignee) is not checked, only id, assigned, and todoItem
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TodoItemTask)) return false;
        TodoItemTask that = (TodoItemTask) o;
        return id == that.id &&
                assigned == that.assigned &&
                java.util.Objects.equals(todoItem, that.todoItem);
    }

    // makes a number based on id, assigned, and todoItem
    // used in hash tables and collections
    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, assigned, todoItem);
    }
}

