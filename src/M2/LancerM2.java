import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerM2 {
    public static void main(String[] args) {
        try {
            ServiceVelib sv = new ServiceVelib();
            ServiceVelibInterface svi = (ServiceVelibInterface) UnicastRemoteObject.exportObject(sv, 54600);
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("M2", svi);
        } catch (Exception e) {
            System.out.println("Serveur exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
