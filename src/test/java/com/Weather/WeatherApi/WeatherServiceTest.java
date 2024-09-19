package com.Weather.WeatherApi;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.Weather.WeatherApi.dto.WeatherResponseDTO;
import com.Weather.WeatherApi.model.Temperature;
import com.Weather.WeatherApi.model.Weather;
import com.Weather.WeatherApi.repository.WeatherRepository;
import com.Weather.WeatherApi.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class WeatherServiceTest {

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetWeatherAndSave() throws IOException {
        // Simular la respuesta de la API
        String jsonResponse = "[{\"LocalObservationDateTime\":\"2024-09-19T13:55:00-03:00\",\"EpochTime\":1695110400,\"WeatherText\":\"Clear\",\"HasPrecipitation\":false,\"Temperature\":{\"Metric\":{\"Value\":20.0,\"Unit\":\"C\",\"UnitType\":17},\"Imperial\":{\"Value\":68.0,\"Unit\":\"F\",\"UnitType\":18}}}]";

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(jsonResponse);

        // Crear el ObjectMapper y JsonNode mockeados
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        JsonNode weatherNode = rootNode.get(0);
        
        // Testar el método
        WeatherResponseDTO response = weatherService.getWeatherAndSave("12345");

        // Verificar que la respuesta es la esperada
        assertNotNull(response);
        
        // Convertir la fecha esperada a un objeto DateTime para comparación
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        ZonedDateTime expectedDateTime = ZonedDateTime.parse("2024-09-19T13:55:00-03:00", formatter);
        ZonedDateTime actualDateTime = ZonedDateTime.parse(response.getObservationDateTime(), formatter);
        
        // Comparar las fechas y horas usando comparación de zonas horarias
        assertEquals(expectedDateTime, actualDateTime);

        assertEquals("Clear", response.getWeatherText());
        assertFalse(response.isHasPrecipitation());

        // Verificar las temperaturas
        Temperature tempMetric = response.getTemperatureMetric();
        assertNotNull(tempMetric);
        assertEquals(20.0, tempMetric.getValue());
        assertEquals("C", tempMetric.getUnit());
        assertEquals(17, tempMetric.getUnitType());

        Temperature tempImperial = response.getTemperatureImperial();
        assertNotNull(tempImperial);
        assertEquals(68.0, tempImperial.getValue());
        assertEquals("F", tempImperial.getUnit());
        assertEquals(18, tempImperial.getUnitType());

        // Verificar que el método save fue llamado en el repositorio
        verify(weatherRepository).save(any(Weather.class));
    }
}
