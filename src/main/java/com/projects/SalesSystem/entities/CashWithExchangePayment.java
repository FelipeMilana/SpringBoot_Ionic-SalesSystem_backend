package com.projects.SalesSystem.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projects.SalesSystem.entities.enums.Bank;

@Entity
@Table(name = "tb_cash_with_exchange_payment")
public class CashWithExchangePayment extends Payment{

	private static final long serialVersionUID = 1L;

	private Integer bank;
	private Double cashValue;
	
	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;
	
	public CashWithExchangePayment() {
	}
	
	public CashWithExchangePayment(Long id, Sale sale, Bank bank, Double cashValue, Vehicle vehicle) {
		super(id, sale);
		this.bank = (bank==null) ? null : bank.getCode();
		this.cashValue = cashValue;
		this.vehicle = vehicle;
	}
	
	public Bank getBank() {
		return Bank.toIntegerEnum(bank);
	}
	
	public void setBank(Bank bank) {
		this.bank = bank.getCode();
	}
	
	public Double getCashValue() {
		return cashValue;
	}
	
	public void setCashValue(Double cashValue) {
		this.cashValue = cashValue;
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}
