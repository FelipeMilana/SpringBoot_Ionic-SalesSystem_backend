package com.projects.SalesSystem.entities.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import com.projects.SalesSystem.entities.Person;

public class PersonDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	private String name;
	
	private String email;
	
	@NotEmpty(message = "Preenchimento Obrigatório")	
	@CPF(message = "CPF inválido")
	private String cpf;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	private String telephone;
	
	private String telephone2;
	
	private AddressDTO address;
	
	public PersonDTO() {
	}
	
	public PersonDTO(Person obj) {
		id = obj.getId();
		name = obj.getName();
		email = obj.getEmail();
		cpf = obj.getCpf();
		telephone = obj.getTelephone();
		telephone2 = obj.getTelephone2();
		address = new AddressDTO(obj.getAddress());
	}

	public PersonDTO(Long id, String name, String email, String cpf, String telephone, String telephone2, AddressDTO address) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpf = cpf;
		this.telephone = telephone;
		this.telephone2 = telephone2;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getTelephone2() {
		return telephone2;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2= telephone2;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}
}