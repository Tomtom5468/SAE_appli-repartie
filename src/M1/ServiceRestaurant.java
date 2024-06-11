import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceRestaurant implements ServiceRestaurantInterface{

    public ServiceRestaurant() {
    }

    @Override
    public List<Restaurant> getRestaurants() {
        java.sql.Connection connection = null;
        try{
            connection = Connection.getConnection(BD.USERNAME, BD.PASSWORD);
            connection.setAutoCommit(false);

            PreparedStatement insertStatement = connection.prepareStatement("select * from restaurants");
            ResultSet resultSet = insertStatement.executeQuery();

            List<Restaurant> restaurants = new ArrayList<>();

            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String nom = resultSet.getString(2);
                String adresse = resultSet.getString(3);
                double latitude = resultSet.getDouble(4);
                double longitude = resultSet.getDouble(5);
                Restaurant restaurant = new Restaurant(id, nom, adresse, latitude, longitude);
                restaurants.add(restaurant);
            }
            connection.commit();
            return restaurants;
        }catch (Exception e){
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error during rollback " + ex.getMessage());
                }
            }
            System.out.println("Error during transaction " + e.getMessage());
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.out.println("Error during connection close " + ex.getMessage());
                }
            }
        }
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        java.sql.Connection connection = null;
        try{
            connection = Connection.getConnection(BD.USERNAME, BD.PASSWORD);
            connection.setAutoCommit(false);

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
            connection.commit();
            return restaurant;
        }catch (Exception e){
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error during rollback " + ex.getMessage());
                }
            }
            System.out.println("Error during transaction " + e.getMessage());
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.out.println("Error during connection close " + ex.getMessage());
                }
            }
        }
    }

    @Override
    public boolean addRestaurant(Restaurant restaurant) {
        java.sql.Connection connection = null;
        try{
            connection = Connection.getConnection(BD.USERNAME, BD.PASSWORD);
            connection.setAutoCommit(false);

            PreparedStatement insertStatement = connection.prepareStatement("insert into restaurants values(?, ?, ?, ?, ?)");
            insertStatement.setInt(1, restaurant.getId());
            insertStatement.setString(2, restaurant.getNom());
            insertStatement.setString(3, restaurant.getAdresse());
            insertStatement.setDouble(4, restaurant.getLatitude());
            insertStatement.setDouble(5, restaurant.getLongitude());
            insertStatement.executeUpdate();

            connection.commit();
            return true;
        }catch (Exception e){
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error during rollback " + ex.getMessage());
                }
            }
            System.out.println("Error during transaction " + e.getMessage());
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.out.println("Error during connection close " + ex.getMessage());
                }
            }
        }
    }

}
