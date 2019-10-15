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

import com.teste.xbrain.entity.Product;
import com.teste.xbrain.repository.ProductRepository;

@RestController
public class ProductController {
	@Autowired
	private ProductRepository _productRepository;
	
	@RequestMapping(value = "/product", method = RequestMethod.GET)
	private Page<Product> Get(Pageable pageable){
		return _productRepository.findAll(pageable);
	}
	
	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> GetById(@PathVariable(value = "id") long id){
		Optional<Product> product = _productRepository.findById(id);
		if(product.isPresent()) {
			return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "product", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Product Post(@Valid @RequestBody Product product) {
		return _productRepository.save(product);
	}
	
	@RequestMapping(value = "product/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Product> Put(@PathVariable( value = "id") long id, @Valid @RequestBody Product newProduct) {
		Optional<Product> oldProduct = _productRepository.findById(id);
		if(oldProduct.isPresent()) {
			Product product = oldProduct.get();
			
			product.setName(newProduct.getName());
			product.setPrice(newProduct.getPrice());
			product.setQuantity(newProduct.getQuantity());
			product.setDescription(newProduct.getDescription());
			
			_productRepository.save(product);
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "product/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id){
		Optional<Product> product = _productRepository.findById(id);
		if(product.isPresent()) {
			_productRepository.delete(product.get());
			 return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
