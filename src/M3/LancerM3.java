import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class LancerM3 {
    public static void main(String[] args) {
        try{
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            server.createContext("/GetAllResto", new GetAllRestaurants());
            server.createContext("/GetOneResto", new GetOneRestaurants());
            server.setExecutor(null); // utilise le gestionnaire par défaut
            server.start();
            System.out.println("Server started on port 8000");
        }catch (Exception e){
            System.out.println("Erreur de connexion " + e.getMessage());
        }
    }
}
