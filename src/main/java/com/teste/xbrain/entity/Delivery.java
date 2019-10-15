package com.teste.xbrain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.None.class, property = "productId", scope = Delivery.class)
public class Delivery implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_sequence")
	@SequenceGenerator(name = "delivery_sequence", sequenceName = "delivery_sequence", initialValue = 1)
    @Column(name = "delivery_id")
    private long deliveryId;

    @OneToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;
    
    @Column(nullable = false)
    private String address;
    
    public Delivery() {
    	super();
    }
    
	public Delivery(
		long deliveryId, 
		Sale sale, 
		String address) {
		super();
		this.deliveryId = deliveryId;
		this.sale = sale;
		this.address = address;
	}

	@JsonGetter("deliveryId")
	public long getDeliveryId() {
		return deliveryId;
	}

	@JsonSetter("deliveryId")
	public void setDeliveryId(long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}    
}