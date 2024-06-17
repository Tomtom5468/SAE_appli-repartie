document.addEventListener('DOMContentLoaded', function () {
    var map = L.map('map').setView([48.6921, 6.1844], 13);

    L.tileLayer('http://{s}.google.com/vt/lyrs=s&x={x}&y={y}&z={z}', {
        maxZoom: 20,
        subdomains: ['mt0', 'mt1', 'mt2', 'mt3'],
        attribution: 'Map data ©2023 Google'
    }).addTo(map);

    fetch('https://transport.data.gouv.fr/gbfs/nancy/gbfs.json')
        .then(response => response.json())
        .then(data => {
            var customIcon = L.icon({
                iconUrl: '../image/velo-icon.png',
                iconSize: [32, 32],
                iconAnchor: [16, 16],
            });
            data.data.fr.feeds.forEach(function(feed) {
                if (feed.name === 'station_information') {
                    fetch(feed.url)
                        .then(response => response.json())
                        .then(data => {
                            var stations = data.data.stations;
                            stations.forEach(function(station) {
                                var marker = L.marker([station.lat, station.lon], {icon: customIcon}).addTo(map);
                                marker.bindPopup(`<h3>${station.name}</h3><p>Adresse: ${station.address}</p>`);
                                marker.id = station.station_id;
                            });
                        })
                        .catch(error => console.error('Erreur lors du chargement des données des stations Vélib:', error));
                }
                if (feed.name === 'station_status') {
                    fetch(feed.url)
                        .then(response => response.json())
                        .then(data => {
                            var stations = data.data.stations;
                            stations.forEach(function(station) {
                                var marker = map._layers[Object.keys(map._layers).find(key => map._layers[key].id === station.station_id)];
                                marker.bindPopup(marker.getPopup().getContent() + `<p>Vélos disponibles: ${station.num_bikes_available}<br>Places de parking libres: ${station.num_docks_available}</p>`);
                            });
                        })
                        .catch(error => console.error('Erreur lors du chargement des données des stations Vélib:', error));
                }
            });

            var cities = [
                { name: "Nancy", coordinates: [48.6921, 6.1844] },
                { name: "Vandœuvre-lès-Nancy", coordinates: [48.6667, 6.1833] },
                { name: "Laxou", coordinates: [48.7, 6.15] },
            ];

            cities.forEach(function(city) {
                L.marker(city.coordinates, {
                    icon: L.divIcon({
                        className: 'city-label',
                        html: `<div>${city.name}</div>`
                    })
                }).addTo(map);
            });
        })
        .catch(error => console.error('Erreur lors du chargement des données des stations Vélib:', error));

    map.on('click', function(e) {
        var lat = e.latlng.lat;
        var lon = e.latlng.lng;
        showAddRestaurantPopup(lat, lon, map);
    });

    const apiUrl = 'http://localhost:8000/GetAllResto';
    Restaurant.fetchRestaurants(apiUrl)
        .then(restaurants => {
            Restaurant.displayRestaurants(map, restaurants);
        })
        .catch(error => {
            console.error('Erreur lors de l\'affichage des restaurants:', error);
        });

    const apiUrlIncident = 'http://localhost:8000/GetIncident';
    Incident.fetchIncidents(apiUrlIncident)
        .then(incidents => {
            Incident.displayIncidents(map, incidents);
        })
        .catch(error => {
            console.error('Erreur lors de l\'affichage des incidents:', error);
        });
});

function showAddRestaurantPopup(lat, lon, map) {
    var popupContent = `
        <h3 class="popup-title">Ajouter un Restaurant</h3>
        <form id="add-restaurant-form" class="popup-form">
            <div class="form-group">
                <label for="nom">Nom :</label>
                <input type="text" class="form-control" id="nom" name="nom" required>
            </div>
            <div class="form-group">
                <label for="adresse">Adresse :</label>
                <input type="text" class="form-control" id="adresse" name="adresse" required>
            </div>
            <div class="form-group">
                <label for="longitude">Longitude :</label>
                <input type="text" class="form-control" id="longitude" name="longitude" value="${lon}" readonly>
            </div>
            <div class="form-group">
                <label for="latitude">Latitude :</label>
                <input type="text" class="form-control" id="latitude" name="latitude" value="${lat}" readonly>
            </div>
            <button type="submit" class="btn btn-primary">Ajouter</button>
        </form>
    `;

    var popup = L.popup()
        .setLatLng([lat, lon])
        .setContent(popupContent);

    map.openPopup(popup);

    document.getElementById('add-restaurant-form').addEventListener('submit', function(event) {
        event.preventDefault();
        var formData = new FormData(event.target);
        fetch('http://localhost:8000/AddRestaurant', {
            method: 'POST',
            body: JSON.stringify({
                nom: formData.get('nom'),
                adresse: formData.get('adresse'),
                latitude: formData.get('latitude'),
                longitude: formData.get('longitude')
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                alert('Restaurant ajouté avec succès');
                map.closePopup();
                const apiUrl = 'http://localhost:8000/GetAllResto';
                Restaurant.fetchRestaurants(apiUrl)
                    .then(restaurants => {
                        Restaurant.displayRestaurants(map, restaurants);
                    })
                    .catch(error => {
                        console.error('Erreur lors de l\'affichage des restaurants:', error);
                    });
            })
            .catch(error => {
                console.error('Erreur lors de l\'ajout du restaurant:', error);
            });
    });
}
