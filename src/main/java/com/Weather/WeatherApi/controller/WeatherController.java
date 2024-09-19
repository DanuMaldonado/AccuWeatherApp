package com.Weather.WeatherApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Weather.WeatherApi.dto.WeatherResponseDTO;
import com.Weather.WeatherApi.model.Weather;
import com.Weather.WeatherApi.repository.WeatherRepository;
import com.Weather.WeatherApi.service.WeatherService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherRepository weatherRepository;
    
    // Endpoint para obtener y guardar el clima de una ubicación
    @GetMapping("/save")
    public WeatherResponseDTO getWeather(@RequestParam String locationKey) {
        return weatherService.getWeatherAndSave(locationKey);
    }

    // Endpoint para obtener todos los registros de clima almacenados
    @GetMapping("/all")
    public List<Weather> getAllWeather() {
        return weatherRepository.findAll();
    }
}
