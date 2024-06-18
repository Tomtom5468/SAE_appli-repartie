document.addEventListener('DOMContentLoaded', function () {
    var map = L.map('map').setView([48.6921, 6.1844], 13);

    L.tileLayer('http://{s}.google.com/vt/lyrs=s&x={x}&y={y}&z={z}', {
        maxZoom: 20,
        subdomains: ['mt0', 'mt1', 'mt2', 'mt3'],
        attribution: 'Map data ©2023 Google'
    }).addTo(map);

    var velibMarkers = [];
    var restaurantMarkers = [];
    var incidentMarkers = [];
    var etablissementMarkers = [];
    var velibMarkersVisible = false;
    var restaurantMarkersVisible = false;
    var incidentMarkersVisible = false;
    var etablissementMarkersVisible = false;

    // Charger les données des stations Vélib
    fetch('https://transport.data.gouv.fr/gbfs/nancy/gbfs.json')
        .then(response => response.json())
        .then(data => {
            var customVelibIcon = L.icon({
                iconUrl: '../image/velo-icon.png',
                iconSize: [32, 32],
                iconAnchor: [16, 16],
            });
            var stationInfoMap = {};

            const stationInfoFeed = data.data.fr.feeds.find(feed => feed.name === 'station_information');
            const stationStatusFeed = data.data.fr.feeds.find(feed => feed.name === 'station_status');

            if (stationInfoFeed) {
                fetch(stationInfoFeed.url)
                    .then(response => response.json())
                    .then(infoData => {
                        var stations = infoData.data.stations;
                        stations.forEach(function (station) {
                            stationInfoMap[station.station_id] = {
                                name: station.name,
                                address: station.address,
                                lat: station.lat,
                                lon: station.lon
                            };
                            var marker = L.marker([station.lat, station.lon], { icon: customVelibIcon });
                            marker.bindPopup(`<h3>${station.name}</h3><p>Adresse: ${station.address}</p><div id="station-info-${station.station_id}"></div>`);
                            marker.id = station.station_id;
                            velibMarkers.push(marker);
                        });
                    })
                    .catch(error => console.error('Erreur lors du chargement des données des stations Vélib:', error));
            }

            if (stationStatusFeed) {
                fetch(stationStatusFeed.url)
                    .then(response => response.json())
                    .then(statusData => {
                        var stations = statusData.data.stations;
                        stations.forEach(function (station) {
                            if (stationInfoMap[station.station_id]) {
                                var marker = velibMarkers.find(m => m.id === station.station_id);
                                if (marker) {
                                    var stationInfo = stationInfoMap[station.station_id];
                                    var popupContent = `
                                        <h3>${stationInfo.name}</h3>
                                        <p>Adresse: ${stationInfo.address}</p>
                                        <p>Vélos disponibles: ${station.num_bikes_available}<br>Places de stationnement de vélo libres : ${station.num_docks_available}</p>
                                        <div>
                                            <a href="https://maps.google.com/?q=${stationInfo.lat},${stationInfo.lon}" target="_blank">
                                                <img src="../image/GoogleMaps.png" alt="Google Maps" width="24" height="22">
                                            </a>
                                            <a href="https://maps.apple.com/?daddr=${stationInfo.lat},${stationInfo.lon}" target="_blank">
                                                <img src="../image/Apple-Plans.webp" alt="Apple Plans" width="40" height="29">
                                            </a>
                                        </div>
                                    `;
                                    marker.setPopupContent(popupContent);
                                }
                            }
                        });
                    })
                    .catch(error => console.error('Erreur lors du chargement des données des statuts des stations Vélib:', error));
            }
        })
        .catch(error => console.error('Erreur lors du chargement des données des stations Vélib:', error));

    function toggleMarkers(markerArray, visible) {
        if (visible) {
            markerArray.forEach(marker => map.removeLayer(marker));
        } else {
            markerArray.forEach(marker => marker.addTo(map));
        }
        return !visible;
    }

    document.getElementById('toggle-velib').parentElement.addEventListener('click', function () {
        velibMarkersVisible = toggleMarkers(velibMarkers, velibMarkersVisible);
    });

    const apiUrlRestaurant = 'http://localhost:8000/GetAllResto';
    Restaurant.fetchRestaurants(apiUrlRestaurant)
        .then(restaurants => {
            const customRestaurantIcon = L.icon({
                iconUrl: '../image/restaurantcarte.png',
                iconSize: [32, 32],
                iconAnchor: [16, 16]
            });

            restaurants.forEach(restaurant => {
                const marker = L.marker([restaurant.latitude, restaurant.longitude], { icon: customRestaurantIcon });
                marker.bindPopup(`
                    <h3>${restaurant.nom}</h3>
                    <p>${restaurant.adresse}</p>
                    <img src="${restaurant.lienImage}" alt="Photo du restaurant">
                    <button class="reservation-button" onclick="openReservationPopup('${restaurant.id}', '${restaurant.nom}')">Réserver</button>
                `);
                restaurantMarkers.push(marker);
            });
        })
        .catch(error => {
            console.error('Erreur lors de l\'affichage des restaurants:', error);
        });

    document.getElementById('toggle-restaurant').parentElement.addEventListener('click', function () {
        restaurantMarkersVisible = toggleMarkers(restaurantMarkers, restaurantMarkersVisible);
    });

    const apiUrlIncident = 'http://localhost:8000/GetIncident';
    Incident.fetchIncidents(apiUrlIncident)
        .then(incidents => {
            const customIncidentIcon = L.icon({
                iconUrl: '../image/incident.png',
                iconSize: [32, 32],
                iconAnchor: [16, 16]
            });

            incidents.forEach(incident => {
                const marker = L.marker([incident.latitude, incident.longitude], { icon: customIncidentIcon });
                marker.bindPopup(`<h3>${incident.shortDescription}</h3><p>${incident.description}</p><p>${incident.street}</p><p>Début: ${incident.startTime}<br>Fin: ${incident.endTime}</p>`);
                incidentMarkers.push(marker);
            });
        })
        .catch(error => {
            console.error('Erreur lors de l\'affichage des incidents:', error);
        });

    document.getElementById('toggle-incident').parentElement.addEventListener('click', function () {
        incidentMarkersVisible = toggleMarkers(incidentMarkers, incidentMarkersVisible);
    });



    const apiUrlEtabSup = 'http://localhost:8000/GetEtabSup';
    Etablissement.fetchEtablissements(apiUrlEtabSup)
        .then(etablissement => {
            const customEtablissementIcon = L.icon({
                iconUrl: '../image/etablissement.png',
                iconSize: [32, 32],
                iconAnchor: [16, 16]
            });

            etablissement.forEach(etablissement => {
                const marker = L.marker([etablissement.latitude, etablissement.longitude], { icon: customEtablissementIcon });
                marker.bindPopup(`<h3>${etablissement.nom}</h3><p>${etablissement.adresse}</p><p>${etablissement.type}</p>`);
                etablissementMarkers.push(marker);
            });
        })
        .catch(error => {
            console.error('Erreur lors de l\'affichage des restaurants:', error);
        });

    document.getElementById('toggle-etablissement').parentElement.addEventListener('click', function () {
        etablissementMarkersVisible = toggleMarkers(etablissementMarkers, etablissementMarkersVisible);
    });




    map.on('click', function(e) {
        var lat = e.latlng.lat;
        var lon = e.latlng.lng;
        showAddRestaurantPopup(lat, lon, map);
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
            .then(response => {
                if (!response.ok) {
                    throw new Error('Échec de la réponse du serveur');
                }
                return response.json();
            })
            .then(data => {
                if (data.success) {
                    alert(data.message);
                    map.closePopup();
                    const apiUrl = 'http://localhost:8000/GetAllResto';
                    Restaurant.fetchRestaurants(apiUrl)
                        .then(restaurants => {
                            Restaurant.displayRestaurants(map, restaurants);
                        })
                        .catch(error => {
                            console.error('Erreur lors de l\'affichage des restaurants:', error);
                        });
                } else {
                    alert(data.message);
                }
            })
            .catch(error => {
                console.error('Erreur lors de l\'ajout du restaurant:', error);
            });
    });
}
