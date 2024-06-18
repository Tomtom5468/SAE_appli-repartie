import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceEtabSupInterface extends Remote {
    // Méthode pour obtenir les données des établissements supérieurs
    String getEtabSup() throws RemoteException;
}
