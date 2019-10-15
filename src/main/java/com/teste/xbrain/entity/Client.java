package com.teste.xbrain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.None.class, property = "clientId", scope = Client.class)
public class Client implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_sequence")
	@SequenceGenerator(name = "client_sequence", sequenceName = "client_sequence", initialValue = 1)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@Column(name = "client_id")
	private long clientId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String address;
	
	public Client() {
		super();
	}

	public Client(long clientId, String name, String email, String address) {
		super();
		this.clientId = clientId;
		this.name = name;
		this.email = email;
		this.address = address;
	}

	@JsonGetter("clientId")
	public long getClientId() {
		return clientId;
	}

	@JsonSetter("clientId")
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}