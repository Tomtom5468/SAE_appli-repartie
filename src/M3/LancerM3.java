import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LancerM3 {
    public static void main(String[] args) {
        try{
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            String host1 = "localhost";
            String host2 = "localhost";

            if (args.length != 0) {
                host1 = args[0];
                host2 = args[1];
            }




            server.createContext("/GetAllResto", new GetAllRestaurants(host1));
            server.createContext("/GetOneResto", new GetOneRestaurants(host1));
            server.createContext("/GetIncident",new GetAccident(host2));
            server.createContext("/GetEtabSup", new GetEtabSup(host2));
            server.createContext("/AddRestaurant", new AddRestaurant(host1));
            server.createContext("/AddReservation", new AddReservation(host1));
            server.setExecutor(null); // utilise le gestionnaire par défaut
            server.start();
            System.out.println("Server started on port 8000");
        }catch (Exception e){
            System.out.println("Erreur de connexion " + e.getMessage());
        }
    }
}
