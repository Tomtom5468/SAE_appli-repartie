import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceDonneesBloqueesInterface extends Remote {
    // Méthode pour obtenir les données des incidents de trafic
    String getTrafficIncidents() throws RemoteException;
    // Méthode pour obtenir les données des établissements supérieurs
    String getEtabSup() throws RemoteException;
}
