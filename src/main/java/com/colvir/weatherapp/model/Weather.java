package com.colvir.weatherapp.model;

import java.util.List;
import java.util.Objects;

public class Weather {

    private Double currentTemperature;

    private List<Double> temperatures;

    public Weather() {
    }

    public Weather(Double currentTemperature, List<Double> temperatures) {
        this.currentTemperature = currentTemperature;
        this.temperatures = temperatures;
    }

    public Double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(Double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public List<Double> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<Double> temperatures) {
        this.temperatures = temperatures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Objects.equals(currentTemperature, weather.currentTemperature) && Objects.equals(temperatures, weather.temperatures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentTemperature, temperatures);
    }

    @Override
    public String toString() {
        return "Weather{" +
                "currentTemperature=" + currentTemperature +
                ", temperatures=" + temperatures +
                '}';
    }
}
