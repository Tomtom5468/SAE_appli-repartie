class Restaurant {
    constructor(id, nom, adresse, latitude, longitude) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    static fetchRestaurants(apiUrl) {
        return fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Erreur HTTP: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                return data.map(restaurant => new Restaurant(
                    restaurant.id,
                    restaurant.nom,
                    restaurant.adresse,
                    restaurant.latitude,
                    restaurant.longitude
                ));
            })
            .catch(error => {
                console.error('Erreur lors de la récupération des restaurants:', error);
                return [];
            });
    }

    static displayRestaurants(map, restaurants) {
        const customIcon = L.icon({
            iconUrl: '../image/restaurantcarte.png',
            iconSize: [32, 32],
            iconAnchor: [16, 16]
        });

        restaurants.forEach(restaurant => {
            const marker = L.marker([restaurant.latitude, restaurant.longitude], { icon: customIcon }).addTo(map);
            marker.bindPopup(`<h3>${restaurant.nom}</h3><p>${restaurant.adresse}</p>`);
        });
    }
}
