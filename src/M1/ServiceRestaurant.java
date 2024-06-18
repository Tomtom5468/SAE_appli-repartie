import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;

public class ServiceRestaurant implements ServiceRestaurantInterface {

    public ServiceRestaurant() {
    }

    @Override
    public String getRestaurants() throws RemoteException {
        java.sql.Connection connection = null;
        try {
            connection = Connection.getConnection(BD.USERNAME, BD.PASSWORD);
            connection.setAutoCommit(false);

            PreparedStatement insertStatement = connection.prepareStatement("select * from restaurants");
            ResultSet resultSet = insertStatement.executeQuery();

            List<Restaurant> restaurants = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String nom = resultSet.getString(2);
                String adresse = resultSet.getString(3);
                double latitude = resultSet.getDouble(4);
                double longitude = resultSet.getDouble(5);
                Restaurant restaurant = new Restaurant(id, nom, adresse, latitude, longitude);
                restaurants.add(restaurant);
            }
            connection.commit();

            JSONArray jsonRestaurants = new JSONArray();
            for (Restaurant restaurant : restaurants) {
                jsonRestaurants.put(restaurant.toJson());
            }

            return jsonRestaurants.toString();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error during rollback " + ex.getMessage());
                }
            }
            System.out.println("Error during transaction " + e.getMessage());
            return "";
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
    public String getRestaurantById(int id) throws RemoteException {
        java.sql.Connection connection = null;
        try {
            connection = Connection.getConnection(BD.USERNAME, BD.PASSWORD);
            connection.setAutoCommit(false);

            PreparedStatement insertStatement = connection.prepareStatement("select * from restaurants where id = ?");
            insertStatement.setInt(1, id);
            ResultSet resultSet = insertStatement.executeQuery();

            Restaurant restaurant = null;

            while (resultSet.next()) {
                int idR = resultSet.getInt(1);
                String nom = resultSet.getString(2);
                String adresse = resultSet.getString(3);
                double latitude = resultSet.getDouble(4);
                double longitude = resultSet.getDouble(5);
                restaurant = new Restaurant(idR, nom, adresse, latitude, longitude);
            }
            connection.commit();

            return restaurant.toJson().toString();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error during rollback " + ex.getMessage());
                }
            }
            System.out.println("Error during transaction " + e.getMessage());
            return "";
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
    public boolean addRestaurant(String nom, String adresse, double latitude, double longitude) throws RemoteException {
        java.sql.Connection connection = null;
        try {
            connection = Connection.getConnection(BD.USERNAME, BD.PASSWORD);
            connection.setAutoCommit(false);

            // Get the last id
            PreparedStatement insertStatement = connection.prepareStatement("select max(id) from restaurants");
            ResultSet resultSet = insertStatement.executeQuery();
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            id++;

            insertStatement = connection.prepareStatement("insert into restaurants values(?, ?, ?, ?, ?)");
            insertStatement.setInt(1, id);
            insertStatement.setString(2, nom);
            insertStatement.setString(3, adresse);
            insertStatement.setDouble(4, latitude);
            insertStatement.setDouble(5, longitude);
            insertStatement.executeUpdate();

            connection.commit();
            return true;
        } catch (Exception e) {
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

    @Override
    public boolean reserveRestaurant(String nom, String prenom, int nbConvives, String telephone, String date, String heure, int restaurantId) throws RemoteException {
        java.sql.Connection connection = null;
        try {
            connection = Connection.getConnection(BD.USERNAME, BD.PASSWORD);
            connection.setAutoCommit(false);


            String dateTimeString = date + " " + heure;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            // Conversion de la chaîne en java.sql.Timestamp
            java.util.Date parsedDate = dateFormat.parse(dateTimeString);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());

            int id = 0;
            PreparedStatement selectStatement = connection.prepareStatement("select max(id) from reservations");
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            id++;

            PreparedStatement insertStatement = connection.prepareStatement("insert into reservations values(?, ?, ?, ?, ?, ?, ?)");
            insertStatement.setInt(1, id);
            insertStatement.setString(2, nom);
            insertStatement.setString(3, prenom);
            insertStatement.setInt(4, nbConvives);
            insertStatement.setString(5, telephone);
            insertStatement.setTimestamp(6, timestamp);
            insertStatement.setInt(7, restaurantId);
            insertStatement.executeUpdate();

            connection.commit();
            return true;
        } catch (Exception e) {
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
