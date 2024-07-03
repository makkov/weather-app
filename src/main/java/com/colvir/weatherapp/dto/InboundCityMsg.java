package com.colvir.weatherapp.dto;

import java.util.Objects;

public class InboundCityMsg {

    private String city;

    public InboundCityMsg() {
    }

    public InboundCityMsg(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InboundCityMsg that = (InboundCityMsg) o;
        return Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(city);
    }
}
