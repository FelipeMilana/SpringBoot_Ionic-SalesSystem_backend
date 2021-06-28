package com.projects.SalesSystem.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projects.SalesSystem.entities.enums.Bank;

@Entity
@Table(name = "tb_exchange_with_cashback")
public class ExchangeWithCashbackPayment extends Payment{

	private static final long serialVersionUID = 1L;

	private Double cashback;
	private Integer bank;
	
	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;
	
	public ExchangeWithCashbackPayment() {
	}
	
	public ExchangeWithCashbackPayment(Long id, Sale sale, Double cashback, Bank bank, Vehicle vehicle) {
		super(id, sale);
		this.cashback = cashback;
		this.bank = (bank==null) ? null : bank.getCode();
		this.vehicle = vehicle;
	}
	
	public Double getCashback() {
		return cashback;	
	}
	
	public void setCashback(Double cashback) {
		this.cashback = cashback;
	}
	
	public Bank getBank() {
		return Bank.toIntegerEnum(bank);
	}
	
	public void setBank(Bank bank) {
		this.bank = bank.getCode();
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}
