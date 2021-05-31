package com.projects.SalesSystem.entities.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class BalanceDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Preenchimento Obrigatório")
	@PositiveOrZero(message = "O valor não pode ser negativo")
	private Double value;
	
	public BalanceDTO() {
	}
	
	public BalanceDTO(Double value) {
		this.value = value;
	}
	
	public Double getValue() {
		return value;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}
}
