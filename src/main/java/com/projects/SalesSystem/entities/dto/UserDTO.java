package com.projects.SalesSystem.entities.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.projects.SalesSystem.entities.User;

public class UserDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Size(min = 3, message = "Deve ter no mínimo 3 letras")
	private String name;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Email
	private String email;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	private String password;
	
	@NotNull(message = "Preenchimento Obrigatório")
	private Double santanderBalance;
	
	@NotNull(message = "Preenchimento Obrigatório")
	private Double nubankBalance;
	
	public UserDTO() {
	}
	
	public UserDTO(User obj) {
		id = obj.getId();
		name = obj.getName();
		email = obj.getEmail();
		password = obj.getPassword();
		santanderBalance = obj.getSantanderBalance();
		nubankBalance = obj.getNubankBalance();
	}

	public UserDTO(Long id, String name, String email, String password, Double santanderBalance, Double nubankBalance) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.santanderBalance = santanderBalance;
		this.nubankBalance = nubankBalance;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getSantanderBalance() {
		return santanderBalance;
	}

	public void setSantanderBalance(Double santanderBalance) {
		this.santanderBalance = santanderBalance;
	}

	public Double getNubankBalance() {
		return nubankBalance;
	}

	public void setNubankBalance(Double nubankBalance) {
		this.nubankBalance = nubankBalance;
	}
}