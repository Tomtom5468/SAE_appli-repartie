import java.rmi.AccessException;
import java.rmi.RemoteException;
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
        } catch (RemoteException e) {
            System.out.println("Erreur de création du serveur " + e.getMessage());
        } catch (AccessException e) {
            System.out.println("Erreur d'accès au serveur " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erreur de connexion " + e.getMessage());
        }

    }
}
