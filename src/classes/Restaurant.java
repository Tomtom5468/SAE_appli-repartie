import java.io.Serializable;

public class Restaurant implements Serializable {

    private int id;
    private String nom;
    private String adresse;
    private double latitude;
    private double longitude;

    /**
     * Constructeur de la classe Restaurant
     * @param id
     * @param nom
     * @param adresse
     * @param latitude
     * @param longitude
     * @return void
     */
    public Restaurant(int id, String nom, String adresse, double latitude, double longitude) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Getter de l'attribut nom
     * @return String
     */
    public String getNom() {
        return nom;
    }
}
