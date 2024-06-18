import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AddReservation implements HttpHandler{
    @Override
    public void handle(HttpExchange t) throws IOException {
        try{
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");

            // Gérer la requête OPTIONS (pré-vérification CORS)
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            // Vérifier si la méthode est POST
            if ("POST".equals(exchange.getRequestMethod())) {
                // Lire le corps de la requête
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                isr.close();

                // Convertir les données en JSON
                JSONObject json = new JSONObject(sb.toString());

                // Extraire les valeurs
                String nom = json.getString("nom");
                String prenom = json.getString("prenom");
                int people = json.getInt("people");
                String date = json.getString("date");
                String time = json.getString("time");
                String tel = json.getString("phone");
                String restaurantName = json.getString("restaurantName");

                // Appeler le service RMI
                Registry reg = LocateRegistry.getRegistry("localhost", 54680);
                ServiceRestaurantInterface service = (ServiceRestaurantInterface) reg.lookup("M1");

                boolean response = service.addReservation(nom, prenom, people, date+time, tel, restaurantName);
                exchange.sendResponseHeaders(200, response ? 0 : -1);
                exchange.getResponseBody().close();
            } else {
                // Méthode non supportée
                exchange.sendResponseHeaders(405, -1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
