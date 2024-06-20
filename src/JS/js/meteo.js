document.addEventListener('DOMContentLoaded', function () {
    const apiKey = '9b9f861f6bbf46c294a132227241106';
    const city = 'Nancy';
    const url = `https://api.weatherapi.com/v1/forecast.json?key=${apiKey}&q=${city}&lang=fr&days=1&hourly=24`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                console.error(`Erreur HTTP: ${response.status} - ${response.statusText}`);
                throw new Error(`Erreur HTTP: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            const weatherContainer = document.getElementById('weather-container');
            const weatherTitle = weatherContainer.querySelector('h2');

            // Météo actuelle
            const currentTemperature = data.current.temp_c;
            const currentDescription = data.current.condition.text;
            const currentIcon = "http:" + data.current.condition.icon.replace('http:', 'https:');

            const currentWeatherHTML = `
                <h3>Météo actuelle</h3>
                <p><img src="${currentIcon}" alt="Icône météo"> ${currentTemperature}°C, ${currentDescription}</p>
                <h3>Météo sur la journée</h3>
            `;

            weatherTitle.insertAdjacentHTML('afterend', currentWeatherHTML);

            // Prévisions horaires
            const forecast = data.forecast.forecastday[0].hour;
            let forecastHTML = '';
            for (let i = 0; i < 24; i++) { // Pour chaque heure de la journée
                const hourData = forecast[i];
                const time = hourData.time.split(' ')[1]; // Extraire l'heure
                const temp = hourData.temp_c;
                const desc = hourData.condition.text;
                const icon = "http:" + hourData.condition.icon.replace('http:', 'https:');
                forecastHTML += `
                    <div data-toggle="tooltip" title="${desc}">
                        <p>${time}</p>
                        <p><img src="${icon}" alt="Icône météo"> ${temp}°C</p>
                    </div>
                `;
            }

            const weatherInfo = document.getElementById('weather-info');
            weatherInfo.innerHTML = forecastHTML;

            // Initialiser les tooltips Bootstrap
            $(function () {
                $('[data-toggle="tooltip"]').tooltip();
            });
        })
        .catch(error => {
            console.error('Erreur lors de la récupération de la météo:', error);
            const weatherContainer = document.getElementById('weather-container');
            weatherContainer.innerHTML = `<p>Erreur lors de la récupération de la météo: ${error.message}</p>`;
        });
});
