import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AddRestaurant implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");

            //récupération des données de la requête
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String query = br.readLine();

            // Créer un objet JSON à partir de la chaîne de requête
            JSONObject json = new JSONObject(query);

            // Extraire les valeurs
            String nom = json.getString("nom");
            String adresse = json.getString("adresse");
            double latitude = json.getDouble("latitude");
            double longitude = json.getDouble("longitude");

            Registry reg = LocateRegistry.getRegistry("localhost", 54680);
            ServiceRestaurantInterface service = (ServiceRestaurantInterface) reg.lookup("M1");

            boolean response = service.addRestaurant(nom,adresse,latitude,longitude);
            exchange.sendResponseHeaders(200, response ? 0 : -1);
            exchange.getResponseBody().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
