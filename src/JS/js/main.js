<!-- Placer ce code dans votre fichier JS -->
document.addEventListener("DOMContentLoaded", function() {
    var restaurantButton = document.getElementById("restaurant-button");
    restaurantButton.addEventListener("click", function() {
        window.location.href = "../html/formulairerestaurant.html";
    });
});

document.addEventListener("DOMContentLoaded", function() {
    var acceuilButton = document.getElementById("acceuil-button");
    acceuilButton.addEventListener("click", function() {
        window.location.href = "../html/acceuil.html";
    });
});
