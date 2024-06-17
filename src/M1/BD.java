import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BD {
    public static final String USERNAME;
    public static final String PASSWORD;

    static {
        Properties props = new Properties();
        try {
            FileInputStream in = new FileInputStream("conf.properties");
            props.load(in);
            in.close();
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Error reading conf.properties");
        }

        USERNAME = props.getProperty("database.username");
        PASSWORD = props.getProperty("database.password");
    }
}