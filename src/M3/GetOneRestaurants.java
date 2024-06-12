import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GetOneRestaurants implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            String path = exchange.getRequestURI().getPath();
            String[] parts = path.split("/");
            int id = -1;
            if (parts.length == 3) {
                try {
                    id = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    // Si l'id n'est pas un entier valide
                    String response = "Invalid ID format";
                    exchange.sendResponseHeaders(400, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                    return;
                }
            }
            
            Registry reg = LocateRegistry.getRegistry("localhost", 54680);
            ServiceRestaurantInterface service = (ServiceRestaurantInterface) reg.lookup("Restaurants");

            String response = service.getRestaurantById(id).toJson().toString();
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
