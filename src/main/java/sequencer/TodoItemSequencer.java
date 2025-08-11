package sequencer;

// // this keeps track of what id number to give the next model.TodoItem
public class TodoItemSequencer {
    private static int currentId;

    public static int nextId() {
        return ++currentId;
    }

    public static int getCurrentId() {
        return currentId;
    }

    public static void setCurrentId(int id) {
        currentId = id;
    }
}
