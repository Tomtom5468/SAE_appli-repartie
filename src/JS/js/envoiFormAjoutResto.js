// Attendre que le document soit complètement chargé
document.addEventListener('DOMContentLoaded', function () {
    // Récupérer le formulaire par son identifiant
    const form = document.getElementById('restaurant-form');

    // Vérifier si le formulaire existe
    if (form) {
        // Ajouter un écouteur d'événements pour la soumission du formulaire
        form.addEventListener('submit', function (event) {
            event.preventDefault(); // Empêcher le comportement par défaut de la soumission du formulaire

            // Récupérer les données du formulaire et les organiser en objet
            const formData = {
                nom: form.nom.value,
                adresse: form.adresse.value,
                longitude: form.longitude.value,
                latitude: form.latitude.value,
            };

            // Envoyer une requête POST à l'API pour ajouter un restaurant
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
                    console.log('Réponse reçue:', data); // Afficher les informations retournées dans la console
                    alert('Restaurant ajouté avec succès');
                    form.reset(); // Réinitialiser le formulaire après une soumission réussie
                })
                .catch(error => {
                    console.error('Erreur lors de l\'ajout du restaurant:', error);
                    alert('Erreur lors de l\'ajout du restaurant');
                });
        });
    } else {
        console.error('Formulaire non trouvé');
    }
});
