package receipBD;
import java.sql.*;

public class RecupData {

    public static Connection connection(String user, String password){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver loaded");
            String url= "jdbc:oracle:thin:@charlemagne.iutnc.univ-lorraine.fr:1521:infodb";
            Connection connection = DriverManager.getConnection(url,user,password);
            System.out.println("Database connected");
            return connection;
        }catch (Exception e){
            System.out.println("Erreur de connexion " + e.getMessage());
            return null;
        }
    }
}
