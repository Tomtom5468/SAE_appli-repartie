function openReservationPopup(restaurantId, restaurantName) {
    document.getElementById('restaurant-name').textContent = restaurantName;
    document.getElementById('restaurant-id').value = restaurantId;
    let today = new Date().toISOString().split('T')[0];
    document.getElementById('date').min = today;
    document.getElementById('date').value = today;
    document.getElementById('time').value = '19:00';
    document.getElementById('people').value = '1';
    document.getElementById('time').addEventListener('input', function (e) {
        let value = e.target.value;
        if (!isValidTime(value)) {
            e.target.setCustomValidity("Veuillez choisir une heure entre 11h et 14h ou entre 18h et 00h");
        } else {
            e.target.setCustomValidity("");
        }
    });

    function isValidTime(value) {
        let [hours, minutes] = value.split(':').map(Number);
        if ((hours >= 11 && hours < 14) || (hours >= 18 && hours < 24) || (hours === 0 && minutes === 0)) {
            return true;
        }
        return false;
    }
    document.getElementById('reservation-popup').style.display = 'block';
}

function closeReservationPopup() {
    document.getElementById('reservation-popup').style.display = 'none';
}

async function sendReservationData(reservationData, host) {
    try {
        const response = await fetch(`http://${host}:8000/AddReservation`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(reservationData)
        });
        return response.status;
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
        const data = await sendReservationData(reservationData, '100.64.80.211');
        if (data === 200) {
            alert('Réservation réussie!');
            document.getElementById('reservation-form').reset();
            closeReservationPopup();
        } else if (data === 912) {
            alert('Le restaurant est complet pour cette date et heure.');
            console.error('Le restaurant est complet pour cette date et heure.');
        } else {
            alert('Erreur lors de la réservation: ' + data.message);
        }
    } catch (error) {
        alert('Erreur lors de l\'envoi de la réservation.');
    }
});
