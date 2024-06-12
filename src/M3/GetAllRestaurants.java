import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class GetAllRestaurants implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try{
            Registry reg = LocateRegistry.getRegistry("localhost", 54680);
            ServiceRestaurantInterface service = (ServiceRestaurantInterface) reg.lookup("Restaurants");
    
            System.out.println(service.getRestaurants().size());
            String response = "Hello, World!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}