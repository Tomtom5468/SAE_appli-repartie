class Incident {
    constructor(id, shortDescription, description, startTime, endTime, latitude, longitude, street) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
    }

    static fetchIncidents(apiUrl) {
        return fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Erreur HTTP: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                return data.incidents.map(incident => new Incident(
                    incident.id,
                    incident.short_description,
                    incident.description,
                    incident.starttime,
                    incident.endtime,
                    ...incident.location.polyline.split(' '),
                    incident.location.street
                ));
            })
            .catch(error => {
                console.error('Erreur lors de la récupération des incidents:', error);
                return [];
            });
    }

    static displayIncidents(map, incidents) {
        const customIcon = L.icon({
            iconUrl: '../image/incident.png', // Assurez-vous que cette icône existe
            iconSize: [32, 32],
            iconAnchor: [16, 16]
        });

        incidents.forEach(incident => {
            const marker = L.marker([incident.latitude, incident.longitude], { icon: customIcon }).addTo(map);
            marker.bindPopup(`<h3>${incident.shortDescription}</h3><p>${incident.description}</p><p>${incident.street}</p><p>Début: ${incident.startTime}<br>Fin: ${incident.endTime}</p>`);
        });
    }
}
