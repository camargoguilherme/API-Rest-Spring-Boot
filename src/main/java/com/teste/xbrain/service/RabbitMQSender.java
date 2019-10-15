package com.teste.xbrain.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.xbrain.entity.Sale;
 
@Service
public class RabbitMQSender {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;	
	
	@Autowired
	private Queue queue;
		
    public void send(Sale sale) throws JsonProcessingException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	String s = objectMapper.writeValueAsString(sale);
    	rabbitTemplate.convertAndSend(queue.getName(), s);
		System.out.println(" [x] sending message < " + s + " >");
    }
}
