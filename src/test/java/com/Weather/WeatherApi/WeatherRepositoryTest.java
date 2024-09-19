package com.Weather.WeatherApi;

import static org.assertj.core.api.Assertions.assertThat;

import com.Weather.WeatherApi.model.Weather;
import com.Weather.WeatherApi.repository.WeatherRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class WeatherRepositoryTest {

    @Autowired
    private WeatherRepository weatherRepository;

    /**
     * Prueba de la funcionalidad para encontrar todos los registros del clima.
     */
    @Test
    void testFindAll() {
    	 // Crear instancias de Weather con datos de ejemplo
        Weather weather1 = new Weather("2024-09-19T00:00:00Z", 1695110400L, "Clear", false, null, false, null, null);
        Weather weather2 = new Weather("2024-09-20T00:00:00Z", 1695196800L, "Cloudy", false, null, false, null, null);
        // Guardar las instancias en la base de datos
        weatherRepository.save(weather1);
        weatherRepository.save(weather2);

        // Recuperar todos los registros de la base de datos
        List<Weather> weathers = weatherRepository.findAll();
        
        assertThat(weathers).hasSize(2);
        // Verificar que los registros del clima sean los esperados
        assertThat(weathers).extracting("weatherText").contains("Clear", "Cloudy");
    }
}
