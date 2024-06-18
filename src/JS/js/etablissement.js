class Etablissement {
    constructor(id, nom, adresse, latitude, longitude, type) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }


    static fetchEtablissements(apiUrl) {
        return fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Erreur HTTP: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                // On prend que les établissements ou etablissement.dep_nom === "Meurthe-et-Moselle"
                return data.results.filter(etablissement => etablissement.dep_nom === "Meurthe-et-Moselle").map((etablissement, index) =>
                    new Etablissement(
                        index,
                        etablissement.ur_lib,
                        etablissement.adresse_uai + ' ' + etablissement.code_postal_uai + ' ' + etablissement.localite_acheminement_uai,
                        etablissement.coordonnees.lat,
                        etablissement.coordonnees.lon,
                        etablissement.implantation_lib
                ));
            })
            .catch(error => {
                console.error('Erreur lors de la récupération des etablissements:', error);
                return [];
            });
    }
}