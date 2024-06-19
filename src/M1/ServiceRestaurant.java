import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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
                String lienImage = resultSet.getString(6);
                Restaurant restaurant = new Restaurant(id, nom, adresse, latitude, longitude, lienImage);
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
                String lienImage = resultSet.getString(6);
                restaurant = new Restaurant(idR, nom, adresse, latitude, longitude, lienImage);
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
    public boolean addRestaurant(String nom, String adresse, double latitude, double longitude, String lienImage) throws RemoteException {
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

            insertStatement = connection.prepareStatement("insert into restaurants values(?, ?, ?, ?, ?, ?)");
            insertStatement.setInt(1, id);
            insertStatement.setString(2, nom);
            insertStatement.setString(3, adresse);
            insertStatement.setDouble(4, latitude);
            insertStatement.setDouble(5, longitude);
            insertStatement.setString(6, lienImage);
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

            Timestamp timestamp = getTimestamp(date, heure);
            Timestamp reservationEndTimestamp = new Timestamp(timestamp.getTime() + 60 * 60 * 1000);

            int id = 0;
            PreparedStatement selectStatement = connection.prepareStatement("select max(id) from reservations");
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            id++;

            PreparedStatement insertStatement = connection.prepareStatement("insert into reservations values(?, ?, ?, ?, ?, ?, ?, ?)");
            insertStatement.setInt(1, id);
            insertStatement.setString(2, nom);
            insertStatement.setString(3, prenom);
            insertStatement.setInt(4, nbConvives);
            insertStatement.setString(5, telephone);
            insertStatement.setTimestamp(6, timestamp);
            insertStatement.setTimestamp(7, reservationEndTimestamp);
            insertStatement.setInt(8, restaurantId);
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
    public boolean isRestaurantAvailable(String date, String heure, int restaurantId) throws RemoteException {
        java.sql.Connection connection = null;
        try {
            connection = Connection.getConnection(BD.USERNAME, BD.PASSWORD);
            connection.setAutoCommit(false);

            Timestamp timestamp = getTimestamp(date, heure);

            Timestamp reservationEndTimestamp = new Timestamp(timestamp.getTime() + 60 * 60 * 1000);


            // On vérifie si le restaurant est disponible  à la date et heure demandée (pas de réservation en cours)
            PreparedStatement selectStatement = connection.prepareStatement("select * from reservations where restaurant_id = ? and (reservation_date <= ? and reservation_date_fin> ?) or (reservation_date < ? and reservation_date_fin> ?)");
            selectStatement.setInt(1, restaurantId);
            selectStatement.setTimestamp(2, timestamp);
            selectStatement.setTimestamp(3, timestamp);
            selectStatement.setTimestamp(4, reservationEndTimestamp);
            selectStatement.setTimestamp(5, reservationEndTimestamp);

            ResultSet resultSet = selectStatement.executeQuery();

            boolean isAvailable = true;
            while (resultSet.next()) {
                isAvailable = false;
            }
            connection.commit();
            return isAvailable;
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

    private Timestamp getTimestamp(String date, String heure) throws Exception {
        String dateTimeString = date + " " + heure;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date parsedDate = dateFormat.parse(dateTimeString);
        return new Timestamp(parsedDate.getTime());
    }

}
