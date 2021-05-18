package com.projects.SalesSystem.entities.dto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.projects.SalesSystem.entities.Person;

public class PersonDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Size(min = 3, max = 50, message = "O campo deve ter entre 3 e 50 letras")
	private String name;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Email(message = "Email inválido")
	private String email;
	
	@NotEmpty(message = "Preenchimento Obrigatório")	
	@CPF(message = "CPF inválido")
	private String cpf;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Size(min = 11, max = 11, message = "Verifique se o ddd foi adicionado")
	private String telephone;
	
	@Valid
	private AddressDTO address;
	
	public PersonDTO() {
	}
	
	public PersonDTO(Person obj) {
		id = obj.getId();
		name = obj.getName();
		email = obj.getEmail();
		cpf = obj.getCpf();
		telephone = obj.getTelephone();
		address = new AddressDTO(obj.getAddress());
	}

	public PersonDTO(Long id, String name, String email, String cpf, String telephone, AddressDTO address) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpf = cpf;
		this.telephone = telephone;
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

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}
}