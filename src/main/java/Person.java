public class Person {
    // Variables
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private AppUser credentials;

    // Constructor
    public Person(int id, String firstName, String lastName, String email) {
        if (firstName == null || lastName == null || email == null) {
            throw new IllegalArgumentException("Fields cannot be null");
        }
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getter = get value, Setter = change value
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException("First name cannot be null");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Last name cannot be null");
        }
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        this.email = email;
    }

    public AppUser getCredentials() {
        return credentials;
    }

    public void setCredentials(AppUser credentials) {
        this.credentials = credentials;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + firstName + " " + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    // checks if this Person is "equal" to another one
    // I don't compare credentials (AppUser) here, just the basic info
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return id == person.id &&
                java.util.Objects.equals(firstName, person.firstName) &&
                java.util.Objects.equals(lastName, person.lastName) &&
                java.util.Objects.equals(email, person.email);
    }

    // makes a number from the fields (used for hash tables and sets)
    // credentials not included in this number
    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, firstName, lastName, email);
    }
}
