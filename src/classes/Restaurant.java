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
     * Getter de l'attribut id
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Getter de l'attribut nom
     * @return String
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de l'attribut adresse
     * @return String
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Getter de l'attribut latitude
     * @return double
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Getter de l'attribut longitude
     * @return double
     */
    public double getLongitude() {
        return longitude;
    }

    public org.json.JSONObject toJson(){
        org.json.JSONObject json = new org.json.JSONObject();
        json.put("id", this.id);
        json.put("nom", this.nom);
        json.put("adresse", this.adresse);
        json.put("latitude", this.latitude);
        json.put("longitude", this.longitude);
        return json;
    }
}
