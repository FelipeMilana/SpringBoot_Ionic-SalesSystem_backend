package com.projects.SalesSystem.entities.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.projects.SalesSystem.entities.Address;

public class AddressDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Size(min = 3, max = 80, message = "O campo deve ter entre 3 e 80 letras")
	private String street;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	private String number;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Size(min = 3, max = 20, message = "O campo deve ter entre 3 e 20 letras")
	private String district;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Size(min = 8, max = 8, message = "O campo deve ter 8 letras")
	private String postalCode;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Size(min = 3, max = 20, message = "O campo deve ter entre 3 e 20 letras")
	private String city;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Size(min = 3, max = 20, message = "O campo deve ter entre 3 e 20 letras")
	private String state;	
	
	public AddressDTO() {
	}
	
	public AddressDTO(Address obj) {
		id = obj.getId();
		street = obj.getStreet();
		number = obj.getNumber();
		district = obj.getDistrict();
		postalCode = obj.getPostalCode();
		city = obj.getCity();
		state = obj.getState();
	}

	public AddressDTO(Long id, String street, String number, String district, String postalCode, String city,
			String state) {
		this.id = id;
		this.street = street;
		this.number = number;
		this.district = district;
		this.postalCode = postalCode;
		this.city = city;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}