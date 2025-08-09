public class TodoItemTask {
    private int id;
    private boolean assigned; // true, if we have someone to do it
    private TodoItem todoItem; // what needs to be done
    private Person assignee;   // person who will do it

    // Constructor
    public TodoItemTask(int id, TodoItem todoItem, Person assignee) {
        if (todoItem == null) {
            throw new IllegalArgumentException("TodoItem cannot be null");
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
            throw new IllegalArgumentException("TodoItem cannot be null");
        }
        this.todoItem = todoItem;
    }

    public Person getAssignee() {
        return assignee;
    }

    // like in the other classes, show relevant info
    public String getSummary() {
        String assigneeName = (assignee != null) ? assignee.getFirstName() + " " + assignee.getLastName() : "No assignee";
        return "{id: " + id + ", assigned: " + assigned + ", todoItem: " + todoItem.getTitle() + ", assignee: " + assigneeName + "}";
    }
}

