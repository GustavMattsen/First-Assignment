import java.util.ArrayList;
import java.util.Collection;

// stores AppUser objects in an ArrayList (in memory)
public class AppUserDAOCollection implements AppUserDAO {
    private Collection<AppUser> appUsers = new ArrayList<>();

    @Override
    public void persist(AppUser appUser) {
        appUsers.add(appUser); // add to list
    }

    @Override
    public AppUser findByUsername(String username) {
        // loop through to find the matching username
        for (AppUser u : appUsers) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null; // if not found
    }

    @Override
    public Collection<AppUser> findAll() {
        return appUsers; // just give the whole list
    }

    @Override
    public void remove(String username) {
        AppUser found = findByUsername(username);
        if (found != null) {
            appUsers.remove(found); // remove from list
        }
    }
}
