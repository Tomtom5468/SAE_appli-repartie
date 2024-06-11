import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceVelibInterface extends Remote {
    // Méthode pour obtenir les données des stations Vélib
    String getVelibData() throws RemoteException;

    // Méthode pour obtenir les données des incidents de trafic
    String getTrafficIncidents() throws RemoteException;
}
