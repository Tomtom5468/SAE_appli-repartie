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

    console.log('Réservation pour :', restaurantName);
    console.log('Nom:', name);
    console.log('Date:', date);
    console.log('Heure:', time);
    console.log('Nombre de personnes:', people);
    closeReservationPopup();
});
