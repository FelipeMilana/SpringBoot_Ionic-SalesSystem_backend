package com.projects.SalesSystem.entities.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projects.SalesSystem.entities.Vehicle;

public class VehicleDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull(message = "Preenchimento Obrigatório")
	private Integer type;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Size(min = 2, message = "Deve ter no mínimo 2 letras")
	private String brand;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Size(min = 2, message = "Deve ter no mínimo 2 letras")
	private String model;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Size(min = 4, max = 4, message = "Deve ter 4 letras")
	private String year;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate date;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Size(min = 7, max = 7, message = "Deve ter 7 letras")
	private String licensePlate;
	
	@NotNull(message = "Preenchimento Obrigatório")
	private String description;
	
	@NotNull(message = "Preenchimento Obrigatório")
	@Positive(message  = "Valor precisa ser positivo")
	private Double paidValue;
	
	@NotNull(message = "Preenchimento Obrigatório")
	private Integer bank;
	
	@NotNull(message = "Preenchimento Obrigatório")
	@Positive(message  = "Valor precisa ser positivo")
	private Double possibleSellValue;
	
	@Valid
	private PersonDTO seller;
	
	private List<ExpenseDTO> expenses = new ArrayList<>();
	
	public VehicleDTO() {
	}
	
	public VehicleDTO(Vehicle obj) {
		id = obj.getId();
		type = obj.getType().getCode();
		brand = obj.getBrand();
		model = obj.getModel();
		year = obj.getYear();
		date = obj.getDate();
		licensePlate = obj.getLicensePlate();
		description = obj.getDescription();
		paidValue = obj.getPaidValue();
		bank = (obj.getBank()==null) ? null : obj.getBank().getCode();
		possibleSellValue = obj.getPossibleSellValue();
		seller = new PersonDTO(obj.getSeller());
		expenses.addAll(obj.getExpenses().stream().map(x -> new ExpenseDTO(x)).collect(Collectors.toList()));
	}

	public VehicleDTO(Long id, Integer type, String brand, String model, String year, LocalDate date,
			String licensePlate, String description, Double paidValue, Integer bank, Double possibleSellValue,
			UserDTO buyer, PersonDTO seller) {
		this.id = id;
		this.type = type;
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.date = date;
		this.licensePlate = licensePlate;
		this.description = description;
		this.paidValue = paidValue;
		this.bank = bank;
		this.possibleSellValue = possibleSellValue;
		this.seller = seller;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPaidValue() {
		return paidValue;
	}

	public void setPaidValue(Double paidValue) {
		this.paidValue = paidValue;
	}

	public Integer getBank() {
		return bank;
	}

	public void setBank(Integer bank) {
		this.bank = bank;
	}

	public Double getPossibleSellValue() {
		return possibleSellValue;
	}

	public void setPossibleSellValue(Double possibleSellValue) {
		this.possibleSellValue = possibleSellValue;
	}

	public PersonDTO getSeller() {
		return seller;
	}

	public void setSeller(PersonDTO seller) {
		this.seller = seller;
	}

	public List<ExpenseDTO> getExpenses() {
		return expenses;
	}
}