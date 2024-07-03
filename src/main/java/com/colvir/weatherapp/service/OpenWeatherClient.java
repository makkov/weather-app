package com.colvir.weatherapp.service;

import com.colvir.weatherapp.dto.openweather.OpenWeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OpenWeatherClient {

    private final String uriPattern;
    private final String apiKey;

    public OpenWeatherClient(
            @Value("${openweatherkey.api.url.pattern}") String uriPattern,
            @Value("${openweatherkey.api.key}") String apiKey) {
        this.uriPattern = uriPattern;
        this.apiKey = apiKey;
    }

    public Double getTemperatureByCity(String city) {
        String uri = String.format(uriPattern, city, apiKey);

        RestClient restClient = RestClient.create();

        OpenWeatherResponse response = restClient.get()
                .uri(uri)
                .retrieve()
                .body(OpenWeatherResponse.class);

        return response.main.temp;
    }
}
