import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

class GetAllRestaurants implements HttpHandler {

        private String host;

    public GetAllRestaurants(String host){
        this.host = host;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            // Permettre les requêtes CORS
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");

            // Si la requête est de type OPTIONS, répondre directement
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            // Récupération des données
            Registry reg = LocateRegistry.getRegistry(this.host, 54680);
            ServiceRestaurantInterface service = (ServiceRestaurantInterface) reg.lookup("M1");
            
            String response = service.getRestaurants();
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
