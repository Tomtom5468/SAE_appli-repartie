import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.json.JSONObject;

public class AddRestaurant implements HttpHandler {
    public AddRestaurant() {
    }

    public void handle(HttpExchange exchange) throws IOException {
        try {
            // Ajout des headers CORS
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                // Lire le corps de la requête
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    requestBody.append(line);
                }
                br.close();
                isr.close();

                // Convertir le corps de la requête en JSONObject
                JSONObject jsonRequest = new JSONObject(requestBody.toString());
                String nom = jsonRequest.getString("nom");
                String adresse = jsonRequest.getString("adresse");
                double latitude = jsonRequest.getDouble("latitude");
                double longitude = jsonRequest.getDouble("longitude");
                String lienImage = jsonRequest.getString("lienImage");

                // Appel du service pour ajouter le restaurant
                Registry registry = LocateRegistry.getRegistry("localhost", 54680);
                ServiceRestaurantInterface service = (ServiceRestaurantInterface) registry.lookup("M1");
                boolean success = service.addRestaurant(nom, adresse, latitude, longitude, lienImage);

                // Préparer la réponse JSON
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("success", success);
                jsonResponse.put("message", success ? "Restaurant ajouté avec succès" : "Échec de l'ajout du restaurant");

                // Envoi de la réponse
                String response = jsonResponse.toString();
                byte[] responseBytes = response.getBytes("UTF-8");
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(success ? 200 : 500, responseBytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(responseBytes);
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1);
        }
    }
}
