import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GetEtabSup implements HttpHandler {

        private String host = "localhost";

    public GetEtabSup(String host){
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
            Registry reg = LocateRegistry.getRegistry(this.host, 54190);
            ServiceDonneesBloqueesInterface service = (ServiceDonneesBloqueesInterface) reg.lookup("M2");

            String response = service.getEtabSup();
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
