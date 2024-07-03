package com.colvir.weatherapp.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class OutboundWeatherMsg {

    private String city;

    private Double currentTemperature;

    private Double avgTemperature;

    public OutboundWeatherMsg(String city, Double currentTemperature, Double avgTemperature) {
        this.city = city;
        this.currentTemperature = currentTemperature;
        this.avgTemperature = avgTemperature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(Double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public Double getAvgTemperature() {
        return avgTemperature;
    }

    public void setAvgTemperature(Double avgTemperature) {
        this.avgTemperature = avgTemperature;
    }

    @Override
    public boolean equals(Object o) {


        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutboundWeatherMsg that = (OutboundWeatherMsg) o;
        return Objects.equals(city, that.city) && Objects.equals(currentTemperature, that.currentTemperature) && Objects.equals(avgTemperature, that.avgTemperature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, currentTemperature, avgTemperature);
    }

    @Override
    public String toString() {
        return "OutboundWeatherMsg{" +
                "city='" + city + '\'' +
                ", currentTemperature=" + currentTemperature +
                ", avgTemperature=" + avgTemperature +
                '}';
    }
}
