import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.json.JSONArray;
import java.util.List;

class GetAllRestaurants implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try{
            Registry reg = LocateRegistry.getRegistry("localhost", 54680);
            ServiceRestaurantInterface service = (ServiceRestaurantInterface) reg.lookup("Restaurants");
            
            List<Restaurant> restaurants = service.getRestaurants();
            JSONArray jsonRestaurants = new JSONArray();
            for (Restaurant restaurant : restaurants) {
                jsonRestaurants.put(restaurant.toJson());
            }
            
            String response = jsonRestaurants.toString();
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}