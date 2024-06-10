package receipBD;
import java.sql.*;

public class recupData {

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

    public static void main(String[] args){
        try{
            Connection connection = connection("root","root");

            PreparedStatement insertStatement = connection.prepareStatement("select * from restaurants");
            /*insertStatement.setString(1, cat);
            insertStatement.setString(2, dateDebut);
            insertStatement.setInt(3, duree);*/
            ResultSet resultSet = insertStatement.executeQuery();

            while (resultSet.next()){
                System.out.println();
                for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++)
                    System.out.print(resultSet.getString(i) + "\t\t");
            }
            connection.close();
        }catch (Exception e){
            System.out.println("Erreur de connexion " + e.getMessage());
        }
    }
}
