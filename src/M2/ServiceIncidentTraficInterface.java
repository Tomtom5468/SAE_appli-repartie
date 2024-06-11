import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceIncidentTraficInterface extends Remote {
    // Méthode pour obtenir les données des incidents de trafic
    String getTrafficIncidents() throws RemoteException;
}
