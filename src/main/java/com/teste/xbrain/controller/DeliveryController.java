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

import com.teste.xbrain.entity.Delivery;
import com.teste.xbrain.repository.DeliveryRepository;

@RestController
public class DeliveryController {
	@Autowired
	private DeliveryRepository _deliveryRepository;
	
	@RequestMapping(value = "/delivery", method = RequestMethod.GET)
	private Page<Delivery> Get(Pageable pageable){
		return _deliveryRepository.findAll(pageable);
	}
	
	@RequestMapping(value = "/delivery/{id}", method = RequestMethod.GET)
	public ResponseEntity<Delivery> GetById(@PathVariable(value = "id") long id){
		Optional<Delivery> delivery = _deliveryRepository.findById(id);
		if(delivery.isPresent()) {
			return new ResponseEntity<Delivery>(delivery.get(), HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/delivery", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Delivery Post(@Valid @RequestBody Delivery delivery) {
		return _deliveryRepository.save(delivery);
	}
	
	@RequestMapping(value = "/delivery/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Delivery> Put(@PathVariable( value = "id") long id, @Valid @RequestBody Delivery newDelivery) {
		Optional<Delivery> oldProduct = _deliveryRepository.findById(id);
		if(oldProduct.isPresent()) {
			Delivery delivery = oldProduct.get();
			

			_deliveryRepository.save(delivery);
			return new ResponseEntity<Delivery>(delivery, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/delivery/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id){
		Optional<Delivery> delivery = _deliveryRepository.findById(id);
		if(delivery.isPresent()) {
			_deliveryRepository.delete(delivery.get());
			 return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
