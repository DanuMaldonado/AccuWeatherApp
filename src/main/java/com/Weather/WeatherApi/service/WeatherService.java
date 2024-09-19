package com.Weather.WeatherApi.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Autowired;

import com.Weather.WeatherApi.dto.WeatherResponseDTO;
import com.Weather.WeatherApi.model.Temperature;
import com.Weather.WeatherApi.model.Weather;
import com.Weather.WeatherApi.repository.WeatherRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servicio para obtener información del clima desde la API de AccuWeather y almacenar los datos en la base de datos.
 */
@Service
public class WeatherService {
	
	   private final WeatherRepository weatherRepository;
	   
	   //TODO Modificar estos valores para utilizarlos por properties
	   private static final String API_KEY = "FhUCjcwN06Eb6KxhZO5EfNiQcCR1CA1q";
	   
	   private static final String ACCUWEATHER_API_URL = "http://dataservice.accuweather.com/currentconditions/v1/";

	   /**
	     * Constructor para inyectar el repositorio de Weather.
	     * 
	     * @param weatherRepository Repositorio de Weather para realizar operaciones CRUD.
	     */
	   @Autowired
	    public WeatherService(WeatherRepository weatherRepository) {
	        this.weatherRepository = weatherRepository;
	    }
	    
	   
	   /**
	     * Obtiene la información del clima desde AccuWeather, la guarda en la base de datos H2 y devuelve una respuesta DTO.
	     * 
	     * @param locationKey Clave de ubicación para consultar el clima.
	     * @return WeatherResponseDTO Objeto que contiene la información relevante del clima.
	     */
	    public WeatherResponseDTO getWeatherAndSave(String locationKey) {
	    	
	    	// Construye la URL para la solicitud HTTP a la API de AccuWeather
	        String url = ACCUWEATHER_API_URL + locationKey + "?apikey=" + API_KEY;
	        
	        // Crea una instancia de RestTemplate para realizar la solicitud HTTP
	        RestTemplate restTemplate = new RestTemplate();
	        String jsonResponse = restTemplate.getForObject(url, String.class);

	        // Crea una instancia de ObjectMapper para procesar el JSON
	        ObjectMapper objectMapper = new ObjectMapper();
	        try {
	            JsonNode rootNode = objectMapper.readTree(jsonResponse);
	            JsonNode weatherNode = rootNode.get(0);

	            // Extrae los datos del nodo de clima
	            String observationDateTime = weatherNode.path("LocalObservationDateTime").asText();
	            Long epochTime = weatherNode.path("EpochTime").asLong();
	            String weatherText = weatherNode.path("WeatherText").asText();
	            boolean hasPrecipitation = weatherNode.path("HasPrecipitation").asBoolean();
	            
	            // Extrae y crea instancias de Temperature para las unidades métrica e imperial
	            double temperatureMetricValue = weatherNode.path("Temperature").path("Metric").path("Value").asDouble();
	            String temperatureMetricUnit = weatherNode.path("Temperature").path("Metric").path("Unit").asText();
	            int temperatureMetricUnitType = weatherNode.path("Temperature").path("Metric").path("UnitType").asInt();
	            
	            double temperatureImperialValue = weatherNode.path("Temperature").path("Imperial").path("Value").asDouble();
	            String temperatureImperialUnit = weatherNode.path("Temperature").path("Imperial").path("Unit").asText();
	            int temperatureImperialUnitType = weatherNode.path("Temperature").path("Im,perial").path("UnitType").asInt();

	            // Crear instancias de Temperature
	            Temperature temperatureMetric = new Temperature(temperatureMetricValue, temperatureMetricUnit, temperatureMetricUnitType);
	            Temperature temperatureImperial = new Temperature(temperatureImperialValue, temperatureImperialUnit, temperatureImperialUnitType);

	            // Crear y guardar entidad Weather (opcional si deseas almacenar en la base de datos)
	            Weather weather = new Weather(observationDateTime, epochTime, weatherText, hasPrecipitation, null, false, temperatureMetric, temperatureImperial);
	            weatherRepository.save(weather);

	            // Devolver los datos más relevantes como JSON
	            return new WeatherResponseDTO(observationDateTime, weatherText, hasPrecipitation, temperatureMetric, temperatureImperial);

	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;  // Devuelve null o puedes lanzar una excepción personalizada
	        }
	    }
	    
}
