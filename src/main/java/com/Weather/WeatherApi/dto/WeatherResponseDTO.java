package com.Weather.WeatherApi.dto;

import com.Weather.WeatherApi.model.Temperature;


/**
 * Esta clase encapsula la información relevante sobre las condiciones climáticas.
 */
public class WeatherResponseDTO {
	// Fecha y hora de la observación del clima
    private String observationDateTime;
    // Descripción textual del clima
    private String weatherText;
    // Indica si hay precipitación (true si hay, false en caso contrario)
    private boolean hasPrecipitation;
    // Temperatura en la unidad métrica
    private Temperature temperatureMetric;
    // Temperatura en la unidad imperial
    private Temperature temperatureImperial;

    
    /**
     * Constructor que inicializa todos los campos de la clase.
     * 
     * @param observationDateTime Fecha y hora de la observación del clima.
     * @param weatherText Descripción textual del clima.
     * @param hasPrecipitation Indica si hay precipitación.
     * @param temperatureMetric Temperatura en la unidad métrica.
     * @param temperatureImperial Temperatura en la unidad imperial.
     */
    public WeatherResponseDTO(String observationDateTime, String weatherText, boolean hasPrecipitation, Temperature temperatureMetric, 
            Temperature temperatureImperial) {
    	
        this.observationDateTime = observationDateTime;
        this.weatherText = weatherText;
        this.hasPrecipitation = hasPrecipitation;
        this.setTemperatureMetric(temperatureMetric);
        this.setTemperatureImperial(temperatureImperial);
        
    }

    // Getters y setters
    public String getObservationDateTime() {
        return observationDateTime;
    }

    public void setObservationDateTime(String observationDateTime) {
        this.observationDateTime = observationDateTime;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }

    public boolean isHasPrecipitation() {
        return hasPrecipitation;
    }

    public void setHasPrecipitation(boolean hasPrecipitation) {
        this.hasPrecipitation = hasPrecipitation;
    }

	public Temperature getTemperatureMetric() {
		return temperatureMetric;
	}

	public void setTemperatureMetric(Temperature temperatureMetric) {
		this.temperatureMetric = temperatureMetric;
	}

	public Temperature getTemperatureImperial() {
		return temperatureImperial;
	}

	public void setTemperatureImperial(Temperature temperatureImperial) {
		this.temperatureImperial = temperatureImperial;
	}
}