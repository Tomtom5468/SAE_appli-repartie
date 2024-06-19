import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerM2 {
    public static void main(String[] args) {
        try {
            ServiceDonneesBloquees sdb = new ServiceDonneesBloquees();
            ServiceDonneesBloqueesInterface sdbi = (ServiceDonneesBloqueesInterface) UnicastRemoteObject.exportObject(sdb, 54600);
            Registry reg = LocateRegistry.createRegistry(54190);
            reg.rebind("M2", sdbi);
        } catch (RemoteException e) {
            System.out.println("Erreur de création du serveur " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erreur de connexion " + e.getMessage());
        }

    }
}
