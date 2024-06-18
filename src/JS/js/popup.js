function openReservationPopup(restaurantId, restaurantName) {
    console.log('Ouverture de la popup de réservation pour le restaurant:', restaurantId, restaurantName);
    document.getElementById('restaurant-name').textContent = restaurantName;
    document.getElementById('restaurant-id').value = restaurantId;
    document.getElementById('reservation-popup').style.display = 'block';
}

function closeReservationPopup() {
    document.getElementById('reservation-popup').style.display = 'none';
}

async function sendReservationData(reservationData) {
    try {
        const response = await fetch('http://localhost:8000/AddReservation', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(reservationData)
        });
        if (response.ok) {
            console.log('Réservation envoyée avec succès.', response.status);
        }
        return response.ok;
    } catch (error) {
        console.error('Erreur lors de l\'envoi de la réservation:', error);
        throw error;
    }
}

document.getElementById('reservation-form').addEventListener('submit', async function (event) {
    event.preventDefault();

    const restaurantId = document.getElementById('restaurant-id').value;
    const nom = document.getElementById('nom').value;
    const prenom = document.getElementById('prenom').value;
    const people = document.getElementById('people').value;
    const phone = document.getElementById('phone').value;
    const date = document.getElementById('date').value;
    const time = document.getElementById('time').value;
    const restaurantName = document.getElementById('restaurant-name').textContent;

    // Créer un objet avec les données de réservation
    const reservationData = {
        restaurantId: restaurantId,
        nom: nom,
        prenom: prenom,
        people: people,
        phone: phone,
        date: date,
        time: time,
        restaurantName: restaurantName
    };

    try {
        const data = await sendReservationData(reservationData);
        if (data) {
            alert('Réservation réussie!');
            closeReservationPopup();
        } else {
            alert('Erreur lors de la réservation: ' + data.message);
        }
    } catch (error) {
        alert('Erreur lors de l\'envoi de la réservation.');
    }
});
