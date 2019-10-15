package com.teste.xbrain.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.None.class, property = "saleId", scope = Sale.class)
public class Sale implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sale_sequence")
	@SequenceGenerator(name = "sale_sequence", sequenceName = "sale_sequence", initialValue = 1)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@Column(name = "sale_id")
	private long saleId;

	@OneToOne
	@JoinColumn(name = "client_id")
	private Client client = new Client();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	@JsonProperty(value = "product")
	private List<Product> product = new ArrayList<Product>();
	
	@Column(nullable = true)
	private int quantity;

	@Column(nullable = true)
	private Double sum;

	@Column(nullable = true)
	private Date date;

	@PrePersist
	public void prePersist() {
		this.date = Date.valueOf(LocalDate.now());
	}

	public Sale() {
		super();
	}

	public Sale(long saleId, Client client, Date date) {
		super();
		this.saleId = saleId;
		this.client = client;
		this.date = date;
	}

	@JsonGetter("saleId")
	public long getSaleId() {
		return saleId;
	}

	@JsonSetter("saleId")
	public void setSaleId(long saleId) {
		this.saleId = saleId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@JsonDeserialize
	public List<Product> getProduct() {
		this.sum = 0D;
		if (!this.product.isEmpty()) {
			this.product.forEach(prod -> {
				if (prod.getPrice() != null)
					this.sum += prod.getPrice();
			});
		}
		return product;
	}

	public void setProduct(List<Product> product) {
		this.sum = 0D;
		if (!this.product.isEmpty()) {
			this.product.forEach(prod -> {
				if (prod.getPrice() != null)
					this.sum += prod.getPrice();
			});
		}
		this.product = product;
	}

	public Boolean addProduct(Product product) {
		this.sum += product.getPrice();
		return this.product.add(product);
	}

	public Boolean removeProduct(Product product) {
		this.sum -= product.getPrice();
		return this.product.remove(product);
	}
}