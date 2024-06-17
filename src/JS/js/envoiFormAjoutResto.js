document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('restaurant-form');

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = {
            nom: form.nom.value,
            adresse: form.adresse.value,
            longitude: form.longitude.value,
            latitude: form.latitude.value,
        };

        fetch('http://localhost:8000/AddRestaurant', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erreur lors de l\'ajout du restaurant');
                }
                return response.json();
            })
            .then(data => {
                alert('Restaurant ajouté avec succès');
                form.reset(); // Reset the form after successful submission
            })
            .catch(error => {
                console.error('Erreur lors de l\'ajout du restaurant:', error);
                alert('Erreur lors de l\'ajout du restaurant');
            });
    });
});
