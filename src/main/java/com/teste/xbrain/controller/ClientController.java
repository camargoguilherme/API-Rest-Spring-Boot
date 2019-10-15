package com.teste.xbrain.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.teste.xbrain.entity.Client;
import com.teste.xbrain.repository.ClientRepository;

@RestController
public class ClientController {
	@Autowired
	private ClientRepository _clientRepository;
	
	@RequestMapping(value = "/client", method = RequestMethod.GET)
	private Page<Client> Get(Pageable pageable){
		return _clientRepository.findAll(pageable);
	}
	
	@RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
	public ResponseEntity<Client> GetById(@PathVariable(value = "id") long id){
		Optional<Client> client = _clientRepository.findById(id);
		if(client.isPresent()) {
			return new ResponseEntity<Client>(client.get(), HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "client", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Client Post(@Valid @RequestBody Client client) {
		return _clientRepository.save(client);
	}
	
	@RequestMapping(value = "client/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Client> Put(@PathVariable( value = "id") long id, @Valid @RequestBody Client newClient) {
		Optional<Client> oldClient = _clientRepository.findById(id);
		if(oldClient.isPresent()) {
			Client client = oldClient.get();
			
			client.setName(newClient.getName());
			client.setEmail(newClient.getEmail());
			client.setAddress(newClient.getAddress());
			
			_clientRepository.save(client);
			return new ResponseEntity<Client>(client, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "client/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id){
		Optional<Client> client = _clientRepository.findById(id);
		if(client.isPresent()) {
			_clientRepository.delete(client.get());
			 return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
