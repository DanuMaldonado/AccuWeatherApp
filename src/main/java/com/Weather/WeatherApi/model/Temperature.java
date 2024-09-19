package com.Weather.WeatherApi.model;

import jakarta.persistence.Embeddable;

/**
 * Clase que representa la temperatura en una unidad específica.
 * Esta clase es embebida en la entidad Weather.
 */
@Embeddable
public class Temperature {
	// Valor de la temperatura
    private double value;
    // Unidad de la temperatura (por ejemplo, "C" para Celsius, "F" para Fahrenheit)
    private String unit;
    // Tipo de unidad 
    private int unitType;

    public Temperature() {}

    /**
     * Constructor que inicializa todos los campos.
     * 
     * @param value Valor de la temperatura.
     * @param unit Unidad de la temperatura.
     * @param unitType Tipo de unidad.
     */
    public Temperature(double value, String unit, int unitType) {
        this.value = value;
        this.unit = unit;
        this.unitType = unitType;
    }

    // Getters y setters
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

	public int getUnitType() {
		return unitType;
	}

	public void setUnitType(int unitType) {
		this.unitType = unitType;
	}
}