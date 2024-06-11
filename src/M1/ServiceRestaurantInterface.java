package M1;
import java.rmi.Remote;
import java.util.List;
import classes.Restaurant;

public interface ServiceRestaurantInterface extends Remote {
    public List<Restaurant> getRestaurants();
    public Restaurant getRestaurantById(int id);
}
