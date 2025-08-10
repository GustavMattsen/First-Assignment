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

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username can't be null or empty");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password can't be null or empty");
        }
        this.password = password;
    }

    public AppRole getRole() {
        return role;
    }

    public void setRole(AppRole role) {
        if (role == null) {
            throw new IllegalArgumentException("Role can't be null");
        }
        this.role = role;
    }

    @Override
    public String toString() {
        // password not shown for safety
        return "AppUser{" +
                "username='" + username + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        // equals means: username and role match (no password check)
        if (this == o) return true;
        if (!(o instanceof AppUser)) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(username, appUser.username) && role == appUser.role;
    }

    @Override
    public int hashCode() {
        // password not included here either
        return Objects.hash(username, role);
    }
}
