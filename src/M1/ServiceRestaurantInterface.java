import java.rmi.Remote;
import java.util.List;

public interface ServiceRestaurantInterface extends Remote {
    public List<Restaurant> getRestaurants();
    public Restaurant getRestaurantById(int id);
    public boolean addRestaurant(Restaurant restaurant);
}
