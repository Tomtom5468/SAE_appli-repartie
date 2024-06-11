import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceRestaurant implements ServiceRestaurantInterface{

    public ServiceRestaurant() {
    }

    @Override
    public List<Restaurant> getRestaurants()throws RemoteException{
        try{
            java.sql.Connection connection = Connection.getConnection(BD.USERNAME, BD.PASSWORD);

            PreparedStatement insertStatement = connection.prepareStatement("select * from restaurants");
            ResultSet resultSet = insertStatement.executeQuery();

            List<Restaurant> restaurants = new ArrayList<>();

            //on convertit chaque ligne en un objet
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String nom = resultSet.getString(2);
                String adresse = resultSet.getString(3);
                double latitude = resultSet.getDouble(4);
                double longitude = resultSet.getDouble(5);
                Restaurant restaurant = new Restaurant(id, nom, adresse, latitude, longitude);
                restaurants.add(restaurant);
            }
            connection.close();
            return restaurants;
        }catch (Exception e){
            System.out.println("Erreur de connexion " + e.getMessage());
            return null;
        }
    }

    @Override
    public Restaurant getRestaurantById(int id) throws RemoteException{
        try{
            java.sql.Connection connection = Connection.getConnection(BD.USERNAME, BD.PASSWORD);

            PreparedStatement insertStatement = connection.prepareStatement("select * from restaurants where id = ?");
            insertStatement.setInt(1, id);
            ResultSet resultSet = insertStatement.executeQuery();

            Restaurant restaurant = null;

            while (resultSet.next()){
                int idR = resultSet.getInt(1);
                String nom = resultSet.getString(2);
                String adresse = resultSet.getString(3);
                double latitude = resultSet.getDouble(4);
                double longitude = resultSet.getDouble(5);
                restaurant = new Restaurant(idR, nom, adresse, latitude, longitude);
            }
            connection.close();
            return restaurant;
        }catch (Exception e){
            System.out.println("Erreur de connexion " + e.getMessage());
            return null;
        }
    }
}
