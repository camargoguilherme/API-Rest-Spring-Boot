package com.teste.xbrain.service;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.xbrain.entity.Delivery;
import com.teste.xbrain.entity.Sale;
import com.teste.xbrain.repository.DeliveryRepository;

@Component
public class RabbitMQConsumer {
	
	@Autowired
	private DeliveryRepository _deliveryRepository;
	
	@RabbitListener(queues = {"${xbrain.rabbitmq.queue}"})
    public void receive(@Payload String fileBody) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(" [x] receiving message < " + fileBody + " >");
        Sale sale = objectMapper.readValue( fileBody, Sale.class);

        Delivery delivery = new Delivery();
        delivery.setSale(sale);
        delivery.setAddress(sale.getClient().getAddress());
		
		_deliveryRepository.save(delivery);
		System.out.println(" [x] saving delivery on database");
    }
}
