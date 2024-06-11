import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServiceRestaurantInterface extends Remote {
    public List<Restaurant> getRestaurants() throws RemoteException;
    public Restaurant getRestaurantById(int id) throws RemoteException;
    public boolean addRestaurant(Restaurant restaurant) throws RemoteException;
    public boolean reserveRestaurant(String nom, String prenom, int nbConvives, String telephone, String date, int restaurantId) throws RemoteException;
}
