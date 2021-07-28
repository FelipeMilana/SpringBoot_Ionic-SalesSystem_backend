package com.projects.SalesSystem.entities.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projects.SalesSystem.entities.Vehicle;

public class ExchangeVehicleDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull(message = "Preenchimento Obrigatório")
	private Integer type;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	private String brand;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	private String model;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	private String version;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	private String fabYear;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	private String modYear;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	private String color;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	private String motor;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate date;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Size(min = 7, max = 7, message = "Deve ter 7 letras")
	private String licensePlate;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Size(min = 17, max = 17, message = "Deve ter 17 letras")
	private String chassi;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Size(min = 11, max = 11, message = "Deve ter 11 letras")
	private String renavam;
	
	private String description;
	
	@NotNull(message = "Preenchimento Obrigatório")
	private Double paidValue;
	
	@NotNull(message = "Preenchimento Obrigatório")
	private Double possibleSellValue;
	
	private PersonDTO seller;
	
	private List<ExpenseDTO> expenses = new ArrayList<>();
	
	public ExchangeVehicleDTO() {
	}
	
	public ExchangeVehicleDTO(Vehicle obj) {
		id = obj.getId();
		type = obj.getType().getCode();
		brand = obj.getBrand();
		model = obj.getModel();
		version = obj.getVersion();
		fabYear = obj.getFabYear();
		modYear = obj.getModYear();
		color = obj.getColor();
		motor = obj.getMotor();
		date = obj.getDate();
		licensePlate = obj.getLicensePlate();
		chassi = obj.getChassi();
		renavam = obj.getRenavam();
		description = obj.getDescription();
		paidValue = obj.getPaidValue();
		possibleSellValue = obj.getPossibleSellValue();
		seller = new PersonDTO(obj.getSeller());
		expenses.addAll(obj.getExpenses().stream().map(x -> new ExpenseDTO(x)).collect(Collectors.toList()));
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

	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getFabYear() {
		return fabYear;
	}
	
	public void setFabYear(String fabYear) {
		this.fabYear = fabYear;
	}
	
	public String getModYear() {
		return modYear;
	}
	
	public void setModYear(String modYear) {
		this.modYear = modYear;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getMotor() {
		return motor;
	}
	
	public void setMotor(String motor) {
		this.motor = motor;
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
	
	public String getChassi() {
		return chassi;
	}
	
	public void setChassi(String chassi) {
		this.chassi = chassi;
	}
	
	public String getRenavam() {
		return renavam;
	}
	
	public void setRenavam(String renavam) {
		this.renavam = renavam;
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