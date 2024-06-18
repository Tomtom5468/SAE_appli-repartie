import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.RemoteException;

public class UseAPI {

    public static String fetchData(String urlString) throws RemoteException {
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
