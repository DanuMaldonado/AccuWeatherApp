package com.Weather.WeatherApi;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.Weather.WeatherApi.controller.WeatherController;
import com.Weather.WeatherApi.dto.WeatherResponseDTO;
import com.Weather.WeatherApi.model.Weather;
import com.Weather.WeatherApi.repository.WeatherRepository;
import com.Weather.WeatherApi.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;


@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @MockBean
    private WeatherRepository weatherRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWeather() throws Exception {
        // Preparar datos de prueba
        WeatherResponseDTO weatherResponseDTO = new WeatherResponseDTO(
                "2024-09-19T00:00:00Z", "Clear", false, null, null
        );
        
        when(weatherService.getWeatherAndSave("12345")).thenReturn(weatherResponseDTO);

        // Ejecutar la solicitud GET
        mockMvc.perform(get("/api/weather/save")
                .param("locationKey", "12345"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.observationDateTime").value("2024-09-19T00:00:00Z"))
                .andExpect(jsonPath("$.weatherText").value("Clear"))
                .andExpect(jsonPath("$.hasPrecipitation").value(false))
                .andDo(print());

        // Verificar la interacción con el servicio
        verify(weatherService).getWeatherAndSave("12345");
    }

    @Test
    void testGetAllWeather() throws Exception {
        // Preparar datos de prueba
        Weather weather1 = new Weather("2024-09-19T00:00:00Z", 1695110400L, "Clear", false, null, false, null, null);
        Weather weather2 = new Weather("2024-09-20T00:00:00Z", 1695196800L, "Cloudy", false, null, false, null, null);

        when(weatherRepository.findAll()).thenReturn(Arrays.asList(weather1, weather2));

        // Ejecutar la solicitud GET
        mockMvc.perform(get("/api/weather/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].weatherText").value("Clear"))
                .andExpect(jsonPath("$[1].weatherText").value("Cloudy"))
                .andDo(print());

        // Verificar la interacción con el repositorio
        verify(weatherRepository).findAll();
    }
}