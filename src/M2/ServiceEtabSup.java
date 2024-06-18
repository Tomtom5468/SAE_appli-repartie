import java.rmi.RemoteException;

public class ServiceEtabSup implements ServiceEtabSupInterface {

    public ServiceEtabSup() {
    }

    public String getEtabSup() throws RemoteException {
        String urlString = "https://data.enseignementsup-recherche.gouv.fr/api/explore/v2.1/catalog/datasets/fr-esr-implantations_etablissements_d_enseignement_superieur_publics/records?limit=20&refine=localisation%3A%22Alsace%20-%20Champagne-Ardenne%20-%20Lorraine%22";
        return UseAPI.fetchData(urlString);
    }
}
