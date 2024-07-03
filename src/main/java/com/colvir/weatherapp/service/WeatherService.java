package com.colvir.weatherapp.service;

import com.colvir.weatherapp.dto.OutboundWeatherMsg;
import com.colvir.weatherapp.model.Weather;
import com.colvir.weatherapp.queue.Producer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private final Producer producer;
    private final OpenWeatherClient openWeatherClient;

    private Map<String, Weather> weatherByCity = new HashMap<>();

    public WeatherService(Producer producer, OpenWeatherClient openWeatherClient) {
        this.producer = producer;
        this.openWeatherClient = openWeatherClient;
    }

    public void addCity(String city) {
        if (!weatherByCity.containsKey(city)) {
            weatherByCity.put(city, new Weather(null, new ArrayList<>()));
        }

        System.out.println(weatherByCity);
    }


    @Scheduled(fixedDelay = 10000)
    public void updateWeather() {
        weatherByCity.keySet().forEach(
                city -> {
                    Double weatherInCity = openWeatherClient.getTemperatureByCity(city);
                    Weather weather = weatherByCity.get(city);
                    weather.setCurrentTemperature(weatherInCity);
                    weatherByCity.get(city).getTemperatures().add(weatherInCity);
                });
    }

    @Scheduled(fixedDelay = 10000)
    public void sendWeather() {
        List<OutboundWeatherMsg> weatherInfos = weatherByCity.entrySet().stream()
                .filter(entry -> entry.getValue().getCurrentTemperature() != null)
                .map(entry -> new OutboundWeatherMsg(
                        entry.getKey(),
                        entry.getValue().getCurrentTemperature(),
                        entry.getValue().getTemperatures().isEmpty() ? null
                                : entry.getValue().getTemperatures().stream()
                                .mapToDouble(value -> value)
                                .average()
                                .getAsDouble()
                ))
                .collect(Collectors.toList());

        if (!weatherInfos.isEmpty()) {
            producer.sendMessage("outbound.queue.weather", weatherInfos);
        }
    }
}
