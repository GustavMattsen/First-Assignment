import java.util.Collection;

// tells us what actions we can do with AppUser objects
public interface AppUserDAO {
    void persist(AppUser appUser); // add a new user
    AppUser findByUsername(String username); // find user by their username
    Collection<AppUser> findAll(); // get all users
    void remove(String username); // remove a user
}
