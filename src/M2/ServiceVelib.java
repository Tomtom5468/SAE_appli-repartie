import java.rmi.RemoteException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServiceVelib implements ServiceVelibInterface {

    public ServiceVelib() {
    }
    public String getVelibData() throws RemoteException {
        String urlString = "https://transport.data.gouv.fr/gbfs/nancy/gbfs.json";
        return fetchData(urlString);
    }

    public String getTrafficIncidents() throws RemoteException {
        String urlString = "https://carto.g-ny.org/data/cifs/cifs_waze_v2.json";
        return fetchData(urlString);
    }

    private String fetchData(String urlString) throws RemoteException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(urlString))
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Erreur : HTTP code erreur : "
                        + response.statusCode());
            }

            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error fetching data", e);
        }
    }
}
