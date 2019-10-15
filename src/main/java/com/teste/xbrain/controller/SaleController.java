package com.teste.xbrain.controller;

import java.util.ArrayList;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teste.xbrain.entity.Client;
import com.teste.xbrain.entity.Product;
import com.teste.xbrain.entity.Sale;
import com.teste.xbrain.repository.ClientRepository;
import com.teste.xbrain.repository.ProductRepository;
import com.teste.xbrain.repository.SaleRepository;
import com.teste.xbrain.service.RabbitMQSender;

@RestController
public class SaleController {
	@Autowired
	private SaleRepository _saleRepository;
	
	@Autowired
	private ProductRepository _productRepository;
	
	@Autowired
	private RabbitMQSender rabbitMQSender;

	@RequestMapping(value = "/sale", method = RequestMethod.GET)
	private Page<Sale> Get(Pageable pageable) {
		return _saleRepository.findAll(pageable);
	}

	@RequestMapping(value = "/sale/{id}", method = RequestMethod.GET)
	public ResponseEntity<Sale> GetById(@PathVariable(value = "id") long id) {
		Optional<Sale> sale = _saleRepository.findById(id);
		if (sale.isPresent()) {
			return new ResponseEntity<Sale>(sale.get(), HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/sale", method = RequestMethod.POST)
	public Sale Post(@Valid @RequestBody Sale sale) throws JsonProcessingException {
//		List<Long> idProduct = new ArrayList<Long>();
//		sale.getProduct().forEach( product ->{
//			idProduct.add(product.getProductId());
//		});
//		List<Product> product = _productRepository.findAllById(idProduct);
//		sale.setProduct(product);
		Sale newSale = _saleRepository.save(sale);
		rabbitMQSender.send(newSale);
		return newSale;
	}

	@RequestMapping(value = "/sale/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Sale> Put(@PathVariable(value = "id") long saleId, @Valid @RequestBody Sale newSale) {
		if (_saleRepository.existsById(saleId)) {
			newSale.setSaleId(saleId);
			return new ResponseEntity<Sale>(_saleRepository.save(newSale), HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/sale/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id) {
		Optional<Sale> sale = _saleRepository.findById(id);
		if (sale.isPresent()) {
			_saleRepository.delete(sale.get());
			return new ResponseEntity<>(HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/sale/{idSale}/product/{idProduct}", method = RequestMethod.PUT)
	public ResponseEntity<Sale> AddProduct(@PathVariable(value = "idSale") long idSale, @PathVariable(value = "idProduct") long idProduct) throws JsonProcessingException {
		Optional<Sale> updateSale = _saleRepository.findById(idSale);
		Optional<Product> product = _productRepository.findById(idProduct);
		if (updateSale.isPresent() && product.isPresent()) {
			updateSale.get().addProduct(product.get());
			return new ResponseEntity<Sale>(updateSale.get(),HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/sale/{idSale}/product/{idProduct}", method = RequestMethod.DELETE)
	public ResponseEntity<Sale> RemoveProduct(@PathVariable(value = "idSale") long idSale, @PathVariable(value = "idProduct") long idProduct) {
		Optional<Sale> updateSale = _saleRepository.findById(idSale);
		Optional<Product> product = _productRepository.findById(idProduct);
		if (updateSale.isPresent() && product.isPresent()) {
			updateSale.get().removeProduct(product.get());
			return new ResponseEntity<Sale>(updateSale.get(),HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
