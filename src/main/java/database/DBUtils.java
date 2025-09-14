package database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtils {
    private static final String PROPS = "/db.properties";
    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream in = DBUtils.class.getResourceAsStream(PROPS)) {
            Properties p = new Properties();
            p.load(in);
            url = p.getProperty("jdbc.url");
            user = p.getProperty("jdbc.user");
            password = p.getProperty("jdbc.password");
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws java.sql.SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
