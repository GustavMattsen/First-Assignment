package sequencer;

// this keeps track of what id number to give the next model.Person
public class PersonSequencer {
    // starts at 0 by default
    private static int currentId;

    // makes a new id by adding 1 to the counter
    public static int nextId() {
        return ++currentId;
    }

    // just see what the counter is at right now
    public static int getCurrentId() {
        return currentId;
    }

    // set the counter manually (if loading from file etc.)
    public static void setCurrentId(int id) {
        currentId = id;
    }
}
