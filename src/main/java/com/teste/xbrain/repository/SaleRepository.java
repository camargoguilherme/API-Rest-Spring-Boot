package com.teste.xbrain.repository;	

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.teste.xbrain.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
	Page<Sale> findAll(Pageable pageable);
}
