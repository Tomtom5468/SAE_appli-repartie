import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.json.JSONObject;
import java.util.List;

public class GetAccident implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            Registry reg = LocateRegistry.getRegistry("localhost", 54190);
            ServiceIncidentTraficInterface service = (ServiceIncidentTraficInterface) reg.lookup("M2");
            
            String response = service.getTrafficIncidents();
            JSONObject jsonAccidents = new JSONObject(response);
            response = jsonAccidents.toString();
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}