package com.projects.SalesSystem.entities.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.projects.SalesSystem.entities.Sale;

public class SaleDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotNull(message = "Preenchimento Obrigatório")
	private LocalDate date;
	
	@NotNull(message = "Preenchimento Obrigatório")
	@Positive(message  = "Valor precisa ser positivo")
	private Double finalValue;
	
	private Double profit;
	
	private VehicleDTO vehicle;
	
	@Valid
	private PersonDTO client;
	
	public SaleDTO() {
	}
	
	public SaleDTO(Sale obj) {
		id = obj.getId();
		date = obj.getDate();
		finalValue = obj.getFinalValue();
		profit = obj.getProfit();
		vehicle = new VehicleDTO(obj.getVehicle());
		client = new PersonDTO(obj.getClient());
	}

	public SaleDTO(Long id, LocalDate date, Double finalValue, Double profit, VehicleDTO vehicle, PersonDTO client,
			UserDTO seller) {
		this.id = id;
		this.date = date;
		this.finalValue = finalValue;
		this.profit = profit;
		this.vehicle = vehicle;
		this.client = client;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getFinalValue() {
		return finalValue;
	}

	public void setFinalValue(Double finalValue) {
		this.finalValue = finalValue;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public VehicleDTO getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleDTO vehicle) {
		this.vehicle = vehicle;
	}

	public PersonDTO getClient() {
		return client;
	}

	public void setClient(PersonDTO client) {
		this.client = client;
	}
}