package com.projects.SalesSystem.entities.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projects.SalesSystem.entities.Withdraw;

public class WithdrawDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotNull(message = "Preenchimento Obrigatório")
	private Integer bank;
	
	@NotNull(message = "Preenchimento Obrigatório")
	@PositiveOrZero(message = "O valor não pode ser negativo")
	private Double value;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate date;

	public WithdrawDTO() {
	}
	
	public WithdrawDTO(Withdraw obj) {
		id = obj.getId();
		bank = obj.getBank().getCode();
		value = obj.getValue();
		date = obj.getDate();
	}
	
	public WithdrawDTO(Long id, Integer bank, Double value, LocalDate date) {
		this.id = id;
		this.bank = bank;
		this.value = value;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getBank() {
		return bank;
	}

	public void setBank(Integer bank) {
		this.bank = bank;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}