package com.colvir.weatherapp.queue;

import com.colvir.weatherapp.dto.InboundCityMsg;
import com.colvir.weatherapp.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    private final WeatherService weatherService;
    private final ObjectMapper objectMapper;

    public Listener(WeatherService weatherService, ObjectMapper objectMapper) {
        this.weatherService = weatherService;
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "inbound.queue.city")
    public void receiveMessage(final Message jsonMessage) throws JMSException, JsonProcessingException {

        InboundCityMsg cityMsg = null;
        if (jsonMessage instanceof TextMessage textMessage) {
            cityMsg = objectMapper.readValue(textMessage.getText(), InboundCityMsg.class);
            weatherService.addCity(cityMsg.getCity());
        }
    }
}
