package com.colvir.weatherapp.service;

import com.colvir.weatherapp.model.Weather;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {

    private final OpenWeatherClient openWeatherClient;

    private Map<String, Weather> weatherByCity = new HashMap<>();

    public WeatherService(OpenWeatherClient openWeatherClient) {
        this.openWeatherClient = openWeatherClient;
    }

    public void addCity(String city) {
        if (!weatherByCity.containsKey(city)) {
            weatherByCity.put(city, new Weather(null, new ArrayList<>()));
        }

        System.out.println(weatherByCity);
    }


    //    @Scheduled(fixedDelay = 10000)
    public void updateWeather() {
        weatherByCity.keySet().forEach(
                city -> {
                    Double weatherInCity = openWeatherClient.getTemperatureByCity(city);
                    Weather weather = weatherByCity.get(city);
                    weather.setCurrentTemperature(weatherInCity);
                    weatherByCity.get(city).getTemperatures().add(weatherInCity);
                });
    }
}
