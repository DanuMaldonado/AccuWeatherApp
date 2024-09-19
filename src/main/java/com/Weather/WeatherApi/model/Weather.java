package com.Weather.WeatherApi.model;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

/**
 * Clase que representa la entidad Weather en la base de datos.
 */
@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para la entidad Weather
    private String observationDateTime;
    private Long epochTime;
    private String weatherText;
    private boolean hasPrecipitation;
    private String precipitationType; 
    private boolean isDayTime;
    
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "temperature_metric_value")),
        @AttributeOverride(name = "unit", column = @Column(name = "temperature_metric_unit")),
        @AttributeOverride(name = "unitType", column = @Column(name = "temperature_metric_unit_type"))
    })
    private Temperature temperatureMetric;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "temperature_imperial_value")),
        @AttributeOverride(name = "unit", column = @Column(name = "temperature_imperial_unit")),
        @AttributeOverride(name = "unitType", column = @Column(name = "temperature_imperial_unit_type"))
    })
    
    private Temperature temperatureImperial;
   


    // Constructor, getters y setters
    public Weather()
    {
    	
    }
    /**
     * Constructor con parámetros para inicializar una instancia de Weather.
     * 
     * @param observationDateTime Fecha y hora de la observación del clima.
     * @param epochTime Tiempo en segundos desde la época Unix.
     * @param weatherText Descripción textual del estado del clima.
     * @param hasPrecipitation Indica si hay precipitación.
     * @param precipitationType Tipo de precipitación (si hay).
     * @param isDayTime Indica si es de día o de noche.
     * @param temperatureMetric Temperatura en la unidad métrica.
     * @param temperatureImperial Temperatura en la unidad imperial.
     */
    public Weather(String observationDateTime, Long epochTime, String weatherText, boolean hasPrecipitation,String precipitationType, 
            boolean isDayTime, Temperature temperatureMetric, 
            Temperature temperatureImperial) {
    	
    		this.observationDateTime = observationDateTime;
    		this.setEpochTime(epochTime);
    		this.weatherText = weatherText;
       		this.hasPrecipitation = hasPrecipitation;
    		this.precipitationType = precipitationType;
    		this.isDayTime = isDayTime;
    		this.temperatureMetric = temperatureMetric;
    		this.temperatureImperial = temperatureImperial;
 
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


		public String getObservationDateTime() {
			return observationDateTime;
		}


		public void setObservationDateTime(String observationDateTime) {
			this.observationDateTime = observationDateTime;
		}
		

		public String getPrecipitationType() {
			return precipitationType;
		}

		public void setPrecipitationType(String precipitationType) {
			this.precipitationType = precipitationType;
		}

		public boolean isDayTime() {
			return isDayTime;
		}

		public void setDayTime(boolean isDayTime) {
			this.isDayTime = isDayTime;
		}

		public Long getEpochTime() {
			return epochTime;
		}

		public void setEpochTime(Long epochTime) {
			this.epochTime = epochTime;
		}

}
