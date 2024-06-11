import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerM1 {
    public static void main(String[] args) {
        try {
            ServiceRestaurant sr = new ServiceRestaurant();
            ServiceRestaurantInterface sri = (ServiceRestaurantInterface) UnicastRemoteObject.exportObject(sr, 54680);

            Registry reg = LocateRegistry.createRegistry(54680);
            reg.rebind("Restaurants", sri);
        }catch(RemoteException e) {
            System.out.println("Erreur de création du serveur " + e.getMessage());
        }catch(AccessException e) {
            System.out.println("Erreur d'accès au serveur " + e.getMessage());
        }catch (Exception e){
            System.out.println("Erreur de connexion " + e.getMessage());
        }
    }
}
