import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServiceRestaurantInterface extends Remote {
    public String getRestaurants() throws RemoteException;
    public String getRestaurantById(int id) throws RemoteException;
    public boolean addRestaurant(String nom, String adresse, double latitude, double longitude) throws RemoteException;
    public boolean reserveRestaurant(String nom, String prenom, int nbConvives, String telephone, String date, String heure, int restaurantId) throws RemoteException;
}
