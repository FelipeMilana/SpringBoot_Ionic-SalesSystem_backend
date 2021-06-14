package com.projects.SalesSystem.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projects.SalesSystem.entities.enums.Bank;

@Entity
@Table(name = "tb_funded_with_exchange_payment")
public class FundedWithExchangePayment extends Payment{

	private static final long serialVersionUID = 1L;

	private String name;
	private String cnpj;
	private String telephone;
	private Double inputValue;
	private Integer inputBank;
	private Double fundedValue;
	private Integer fundedBank;
	
	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	public Vehicle vehicle;
	
	public FundedWithExchangePayment() {
	}
	
	public FundedWithExchangePayment(Long id, Sale sale, String name, String cnpj, String telephone, Double inputvalue, 
			Bank inputBank, Double fundedValue, Bank fundedBank, Vehicle vehicle) {
		super(id, sale);
		this.name = name;
		this.cnpj = cnpj;
		this.telephone = telephone;
		this.inputValue = inputvalue;
		this.inputBank = (inputBank == null) ? null : inputBank.getCode();
		this.fundedValue = fundedValue;
		this.fundedBank = (fundedBank == null) ? null : fundedBank.getCode();
		this.vehicle = vehicle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Double getInputValue() {
		return inputValue;
	}

	public void setInputValue(Double inputValue) {
		this.inputValue = inputValue;
	}

	public Bank getInputBank() {
		return Bank.toIntegerEnum(inputBank);
	}
	
	public void setInputBank(Bank inputBank) {
		this.inputBank = inputBank.getCode();
	}
	
	public Double getFundedValue() {
		return fundedValue;
	}

	public void setFundedValue(Double fundedValue) {
		this.fundedValue = fundedValue;
	}
	
	public Bank getFundedBank() {
		return Bank.toIntegerEnum(fundedBank);
	}
	
	public void setFundedBank(Bank fundedBank) {
		this.fundedBank = fundedBank.getCode();
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}