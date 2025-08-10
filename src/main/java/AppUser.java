import java.util.Objects;

public class AppUser {
    // username = like a login name
    private String username;
    // password = secret (don't show in print)
    private String password;
    // role = tells if normal user or admin
    private AppRole role;

    // Constructor
    public AppUser(String username, String password, AppRole role) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username can't be null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password can't be null or empty");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role can't be null");
        }
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
