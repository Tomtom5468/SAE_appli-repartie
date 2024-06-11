import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LancerM3 {
    public static void main(String[] args) {
        try{
            Registry reg = LocateRegistry.getRegistry("localhost", 54680);
            ServiceRestaurantInterface service = (ServiceRestaurantInterface) reg.lookup("Restaurants");
    
            System.out.println(service.getRestaurants().size());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
