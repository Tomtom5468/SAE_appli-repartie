function openReservationPopup(restaurantId, restaurantName) {
    document.getElementById('restaurant-name').textContent = restaurantName;
    document.getElementById('reservation-popup').style.display = 'block';
}

function closeReservationPopup() {
    document.getElementById('reservation-popup').style.display = 'none';
}

document.getElementById('reservation-form').addEventListener('submit', function (event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const date = document.getElementById('date').value;
    const time = document.getElementById('time').value;
    const people = document.getElementById('people').value;
    const restaurantName = document.getElementById('restaurant-name').textContent;
    // Créer un objet avec les données de réservation
    const reservationData = {
        name: name,
        date: date,
        time: time,
        people: people,
        restaurantName: restaurantName
    };
    // Envoyer les données de réservation à l'API
    fetch('http://localhost:8000/AddReservation', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reservationData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('Réservation réussie!');
                closeReservationPopup();
            } else {
                alert('Erreur lors de la réservation: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Erreur lors de l\'envoi de la réservation:', error);
            alert('Erreur lors de l\'envoi de la réservation.');
        });
});
