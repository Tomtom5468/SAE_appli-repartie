import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServiceRestaurantInterface extends Remote {
    public List<Restaurant> getRestaurants() throws RemoteException;
    public Restaurant getRestaurantById(int id) throws RemoteException;
}
