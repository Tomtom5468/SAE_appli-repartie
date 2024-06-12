var map = L.map('map').setView([48.6921, 6.1844], 13);

L.tileLayer('http://{s}.google.com/vt/lyrs=s&x={x}&y={y}&z={z}', {
    maxZoom: 20,
    subdomains: ['mt0', 'mt1', 'mt2', 'mt3'],
    attribution: 'Map data ©2023 Google'
}).addTo(map);

fetch('https://api.cyclocity.fr/contracts/nancy/gbfs/station_information.json')
    .then(response => response.json())
    .then(data => {
        var customIcon = L.icon({
            iconUrl: '../image/velo-icon.png', // Chemin vers l'icône de vélo
            iconSize: [32, 32], // Taille de l'icône
            iconAnchor: [16, 16], // Point d'ancrage de l'icône
        });
        data.data.stations.forEach(function(station) {
            var marker = L.marker([station.lat, station.lon], {icon: customIcon}).addTo(map);
            marker.bindPopup(`<h3>${station.name}</h3><p>Vélos disponibles: ${station.num_bikes_available}<br>Places de parking libres: ${station.num_docks_available}</p>`);
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


