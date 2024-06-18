import java.rmi.RemoteException;

public class ServiceEtabSup implements ServiceEtabSupInterface {

    public ServiceEtabSup() {
    }

    public String getEtabSup() throws RemoteException {
        String urlString = "https://data.enseignementsup-recherche.gouv.fr/explore/dataset/fr-esr-principaux-etablissements-enseignement-superieur/download/?format=json&timezone=Europe/Berlin&lang=fr";
        return UseAPI.fetchData(urlString);
    }
}
