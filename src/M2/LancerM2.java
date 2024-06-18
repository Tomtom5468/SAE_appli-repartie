import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerM2 {
    public static void main(String[] args) {
        try {
            ServiceIncidentTrafic sit = new ServiceIncidentTrafic();
            ServiceIncidentTraficInterface siti = (ServiceIncidentTraficInterface) UnicastRemoteObject.exportObject(sit, 54600);

            ServiceEtabSup ses = new ServiceEtabSup();
            ServiceEtabSupInterface sesi = (ServiceEtabSupInterface) UnicastRemoteObject.exportObject(ses, 54601);
            Registry reg = LocateRegistry.createRegistry(54190);
            reg.rebind("siti", siti);
            reg.rebind("sesi", sesi);
        } catch (RemoteException e) {
            System.out.println("Erreur de création du serveur " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erreur de connexion " + e.getMessage());
        }

    }
}
