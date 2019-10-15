package com.teste.xbrain.repository;	

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.teste.xbrain.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> { 
	Page<Delivery> findAll(Pageable pageable);
}
