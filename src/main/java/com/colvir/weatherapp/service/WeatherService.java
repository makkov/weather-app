package com.colvir.weatherapp.service;

import com.colvir.weatherapp.dto.OutboundWeatherMsg;
import com.colvir.weatherapp.model.Weather;
import com.colvir.weatherapp.queue.Producer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private final Producer producer;

    private Map<String, Weather> weatherByCity = new HashMap<>();

    public WeatherService(Producer producer) {
        this.producer = producer;
    }

    public void addCity(String city) {
        if (!weatherByCity.containsKey(city)) {
            weatherByCity.put(city, new Weather(null, new ArrayList<>()));
        }

        System.out.println(weatherByCity);
    }


    @Scheduled(fixedDelay = 15000)
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
                                .getAsDouble(),
                        LocalDateTime.now()
                ))
                .collect(Collectors.toList());

        if (!weatherInfos.isEmpty()) {
            producer.sendMessage("outbound.queue.weather", weatherInfos);
        }
    }
}
