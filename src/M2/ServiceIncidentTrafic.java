import java.rmi.RemoteException;

public class ServiceIncidentTrafic implements ServiceIncidentTraficInterface {

    public ServiceIncidentTrafic() {
    }

    public String getTrafficIncidents() throws RemoteException {
        String urlString = "https://carto.g-ny.org/data/cifs/cifs_waze_v2.json";
        return UseAPI.fetchData(urlString);
    }

}
