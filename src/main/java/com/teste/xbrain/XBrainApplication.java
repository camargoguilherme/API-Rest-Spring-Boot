package com.teste.xbrain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.xbrain.entity.Client;
import com.teste.xbrain.entity.Product;
import com.teste.xbrain.entity.Sale;
import com.teste.xbrain.repository.ClientRepository;
import com.teste.xbrain.repository.ProductRepository;
import com.teste.xbrain.repository.SaleRepository;

@SpringBootApplication
public class XBrainApplication implements CommandLineRunner {

	@Value("${xbrain.rabbitmq.queue}")
	private String queue;

	@Autowired
	private SaleRepository _saleRepository;

	@Autowired
	private ProductRepository _productRepository;

	@Autowired
	private ClientRepository _clientRepository;

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		SpringApplication.run(XBrainApplication.class, args);
	}

	@Bean
	public Queue queue() {
		return new Queue(queue, true);
	}

	@Override
	public void run(String... args) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		File fileClient = ResourceUtils.getFile("classpath:client.json");
		File fileProduct = ResourceUtils.getFile("classpath:product.json");

		List<Client> clientList = Arrays.asList(objectMapper.readValue(fileClient, Client[].class));
		List<Product> productList = Arrays.asList(objectMapper.readValue(fileProduct, Product[].class));

		_clientRepository.saveAll(clientList);
		_productRepository.saveAll(productList);
		
	}

}
